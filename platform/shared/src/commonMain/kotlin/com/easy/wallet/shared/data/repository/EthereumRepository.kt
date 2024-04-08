package com.easy.wallet.shared.data.repository

import com.easy.wallet.core.commom.DateTimeDecoder
import com.easy.wallet.core.commom.cleanHexPrefix
import com.easy.wallet.core.commom.ifNullOrBlank
import com.easy.wallet.model.TokenBasicResult
import com.easy.wallet.network.source.blockchair.BlockchairApi
import com.easy.wallet.network.source.etherscan.EtherscanApi
import com.easy.wallet.network.source.etherscan.dto.EtherTransactionDto
import com.easy.wallet.network.source.evm_rpc.JsonRpcApi
import com.easy.wallet.shared.asHex
import com.easy.wallet.shared.model.fees.EthereumFee
import com.easy.wallet.shared.model.fees.FeeLevel
import com.easy.wallet.shared.model.fees.FeeModel
import com.easy.wallet.shared.model.transaction.Direction
import com.easy.wallet.shared.model.transaction.EthereumTransactionUiModel
import com.easy.wallet.shared.model.transaction.TransactionStatus
import com.easy.wallet.shared.model.transaction.TransactionUiModel
import com.ionspin.kotlin.bignum.decimal.RoundingMode
import com.ionspin.kotlin.bignum.decimal.toBigDecimal
import com.ionspin.kotlin.bignum.integer.toBigInteger
import com.trustwallet.core.AnySigner
import com.trustwallet.core.CoinType
import com.trustwallet.core.ethereum.SigningInput
import com.trustwallet.core.ethereum.SigningOutput
import com.trustwallet.core.ethereum.Transaction
import com.trustwallet.core.ethereum.TransactionMode
import com.trustwallet.core.sign
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import okio.ByteString

class EthereumRepository internal constructor(
    private val blockchairApi: BlockchairApi,
    private val etherscanApi: EtherscanApi,
    private val jsonRpcApi: JsonRpcApi
) : TokenRepository {
    override suspend fun loadBalance(account: String, contract: String?): String {
        val balance = jsonRpcApi.getBalance(account, contract)
        return balance.cleanHexPrefix().toBigInteger(16).toString()
    }

    override suspend fun loadTransactions(
        token: TokenBasicResult,
        page: Int,
        offset: Int
    ): List<TransactionUiModel> {
        val account = token.address
        val isContract = !token.contract.isNullOrBlank()
        val tnxDto = if (isContract) {
            etherscanApi.getContractInternalTransactions(
                page,
                offset,
                account,
                token.contract.orEmpty()
            )
        } else {
            etherscanApi.getTransactions(page, offset, account)
        }
        return tnxDto.map { it.asTransactionUiModel(token, account) }
    }

    override suspend fun prepFees(
        account: String,
        toAddress: String,
        contractAddress: String?,
        amount: String
    ): List<FeeModel> = withContext(Dispatchers.IO) {
        val estimateGasDeferred = async { estimateGas(account, toAddress, contractAddress, amount) }
        val feeDeferred = async {
            calculateFee()
        }

        val estimateGas = estimateGasDeferred.await()
        val (maxFeePerGas, inclusionFeePerGas) = feeDeferred.await()

        listOf(
            EthereumFee(feeLevel = FeeLevel.Low, gas = estimateGas, maxFeePerGas = maxFeePerGas, priorityFeeGas = inclusionFeePerGas),
            EthereumFee(feeLevel = FeeLevel.Average, gas = estimateGas, maxFeePerGas = maxFeePerGas, priorityFeeGas = inclusionFeePerGas),
            EthereumFee(feeLevel = FeeLevel.Fast, gas = estimateGas, maxFeePerGas = maxFeePerGas, priorityFeeGas = inclusionFeePerGas)
        )
    }

    override suspend fun signTransaction(
        account: String,
        chainId: String,
        privateKey: ByteArray,
        toAddress: String,
        contractAddress: String?,
        amount: String,
        fee: FeeModel
    ): String = withContext(Dispatchers.IO) {
        if (fee !is EthereumFee) throw IllegalArgumentException("fee model is incorrect")
        val isEthNormalTransfer = contractAddress.isNullOrBlank()

        val nonceDeferred = async {
            fetchingNonce(account)
        }
        val nonce = nonceDeferred.await()

        val maxFeePerGas = fee.maxFeePerGas
        val inclusionFeePerGas = fee.priorityFeeGas
        val gasLimit = fee.gas

        // double check balance if enough to send for fee

        val signingInput = SigningInput(
            private_key = ByteString.of(*privateKey),
            to_address = contractAddress.ifNullOrBlank { toAddress },
            tx_mode = TransactionMode.Enveloped,
            chain_id = chainId.asHex(),
            nonce = nonce.asHex(),
            max_fee_per_gas = maxFeePerGas.asHex(),
            max_inclusion_fee_per_gas = inclusionFeePerGas.asHex(),
            gas_limit = gasLimit.asHex(),
            transaction = if (isEthNormalTransfer) {
                Transaction(
                    transfer = Transaction.Transfer(
                        amount = amount.asHex()
                    )
                )
            } else Transaction(
                erc20_transfer = Transaction.ERC20Transfer(
                    to = toAddress,
                    amount = amount.asHex()
                )
            )
        )
        val output = AnySigner.sign(signingInput, CoinType.Ethereum, SigningOutput.ADAPTER)
        output.encoded.hex().let { "0x$it" }
    }

    private suspend fun estimateGas(
        account: String,
        toAddress: String,
        contractAddress: String?,
        amount: String
    ): String {
        val data = if (contractAddress.isNullOrBlank()) {
            ""
        } else {
            Transaction.ERC20Transfer(
                to = toAddress,
                amount = amount.asHex()
            ).encode().decodeToString()
        }
        val estimateGas = jsonRpcApi.estimateGas(
            from = account,
            to = contractAddress.ifNullOrBlank { toAddress },
            value = null,
            data = data
        )
        return estimateGas
    }

    private suspend fun calculateFee(): Pair<String, String> {
        return jsonRpcApi.feeHistory(5, listOf(20, 30))
    }

    private suspend fun fetchingNonce(account: String): String {
        val tnxCount = jsonRpcApi.getTransactionCount(account)
        return tnxCount
    }
}

private fun EtherTransactionDto.asTransactionUiModel(
    token: TokenBasicResult,
    account: String
): EthereumTransactionUiModel {
    val direction = if (from.equals(account, true)) Direction.SEND else Direction.RECEIVE
    return EthereumTransactionUiModel(
        hash = hash,
        amount = value.toBigDecimal().moveDecimalPoint(-token.decimals)
            .roundToDigitPositionAfterDecimalPoint(8, RoundingMode.ROUND_HALF_CEILING)
            .toPlainString(),
        recipient = to,
        sender = from,
        direction = direction,
        gasPrice = gasPrice,
        gas = gas,
        gasUsed = gasUsed,
        symbol = token.symbol,
        functionName = functionName,
        datetime = timeStamp.toLongOrNull()
            ?.let { DateTimeDecoder.decodeToDateTime(it.times(1000)).toString() } ?: "- -",
        status = TransactionStatus.Confirmed
    )
}
