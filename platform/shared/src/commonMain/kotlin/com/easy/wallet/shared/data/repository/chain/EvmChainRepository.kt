package com.easy.wallet.shared.data.repository.chain

import com.easy.wallet.core.commom.cleanHexPrefix
import com.easy.wallet.core.commom.ifNullOrBlank
import com.easy.wallet.model.asset.AssetCoin
import com.easy.wallet.network.source.etherscan.EtherscanApi
import com.easy.wallet.network.source.evm_rpc.JsonRpcApi
import com.easy.wallet.shared.asHex
import com.easy.wallet.shared.model.fees.EthereumFee
import com.easy.wallet.shared.model.fees.FeeLevel
import com.easy.wallet.shared.model.fees.FeeModel
import com.easy.wallet.shared.model.transaction.TransactionUiModel
import com.easy.wallet.shared.model.transaction.asTransactionUiModel
import com.ionspin.kotlin.bignum.integer.toBigInteger
import com.trustwallet.core.AnySigner
import com.trustwallet.core.CoinType
import com.trustwallet.core.ethereum.SigningInput
import com.trustwallet.core.ethereum.SigningOutput
import com.trustwallet.core.ethereum.Transaction
import com.trustwallet.core.ethereum.TransactionMode
import com.trustwallet.core.sign
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import okio.ByteString

class EvmChainRepository internal constructor(
    private val etherscanApi: EtherscanApi,
    private val jsonRpcApi: JsonRpcApi
) : OnChainRepository {
    override suspend fun loadBalance(account: String, contract: String?): String {
        val balance = jsonRpcApi.getBalance(account, contract)
        return balance.cleanHexPrefix().toBigInteger(16).toString()
    }

    override suspend fun loadTransactions(
        coin: AssetCoin,
        page: Int,
        offset: Int
    ): List<TransactionUiModel> {
        val account = coin.address
        val isContract = !coin.contract.isNullOrBlank()
        val basicTransactions = if (isContract) {
            etherscanApi.getContractInternalTransactions(
                page,
                offset,
                account,
                coin.contract.orEmpty()
            )
        } else {
            etherscanApi.getTransactions(page, offset, account)
        }
        return basicTransactions.map { it.asTransactionUiModel(coin, -coin.decimalPlace, account) }
    }

    override suspend fun prepFees(
        account: String,
        toAddress: String,
        contractAddress: String?,
        amount: String
    ): List<FeeModel> = withContext(Dispatchers.Default) {
        val estimateGasDeferred = async { estimateGas(account, toAddress, contractAddress, amount) }
        val feeDeferred = async { calculateFee() }

        val estimateGas = estimateGasDeferred.await()
        val (maxFeePerGas, inclusionFeePerGas) = feeDeferred.await()

        listOf(
            EthereumFee(
                feeLevel = FeeLevel.Low,
                gas = estimateGas,
                maxFeePerGas = maxFeePerGas,
                priorityFeeGas = inclusionFeePerGas
            ),
            EthereumFee(
                feeLevel = FeeLevel.Average,
                gas = estimateGas,
                maxFeePerGas = maxFeePerGas,
                priorityFeeGas = inclusionFeePerGas
            ),
            EthereumFee(
                feeLevel = FeeLevel.Fast,
                gas = estimateGas,
                maxFeePerGas = maxFeePerGas,
                priorityFeeGas = inclusionFeePerGas
            )
        )
    }

    override suspend fun signAndBroadcast(
        account: String,
        chainId: String,
        privateKey: ByteArray,
        toAddress: String,
        contractAddress: String?,
        amount: String,
        fee: FeeModel
    ): String = withContext(Dispatchers.Default) {
        if (fee !is EthereumFee) throw IllegalArgumentException("fee model is incorrect")
        val isEthNormalTransfer = contractAddress.isNullOrBlank()

        val nonceDeferred = async { fetchingNonce(account) }
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
        val encoded = "0x${output.encoded.hex()}"
        jsonRpcApi.sendRawTransaction(encoded)
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
            val amountUInt = (ZERO_UINT + amount.cleanHexPrefix()).takeLast(64)
            "0xa9059cbb000000000000000000000000${toAddress.cleanHexPrefix()}$amountUInt"
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
        return jsonRpcApi.getTransactionCount(account)
    }
}

private const val ZERO_UINT = "0000000000000000000000000000000000000000000000000000000000000000"
