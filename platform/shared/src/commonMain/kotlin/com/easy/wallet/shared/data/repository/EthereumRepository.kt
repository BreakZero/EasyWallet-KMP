package com.easy.wallet.shared.data.repository

import com.easy.wallet.core.commom.DateTimeDecoder
import com.easy.wallet.model.TokenBasicResult
import com.easy.wallet.network.source.blockchair.BlockchairApi
import com.easy.wallet.network.source.etherscan.EtherscanApi
import com.easy.wallet.network.source.etherscan.dto.EtherTransactionDto
import com.easy.wallet.network.source.evm_rpc.JsonRpcApi
import com.easy.wallet.shared.asHex
import com.easy.wallet.shared.model.Balance
import com.easy.wallet.shared.model.FeeLevel
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
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import okio.ByteString

class EthereumRepository internal constructor(
    private val blockchairApi: BlockchairApi,
    private val etherscanApi: EtherscanApi,
    private val jsonRpcApi: JsonRpcApi
) : TokenRepository {
    override fun dashboard(account: String): Flow<List<Balance>> {
        return flow {
            val dashboard = blockchairApi.getDashboardByAccount("ethereum", account)
            val result = dashboard?.let {
                val coinBalance = Balance(
                    address = "",
                    decimal = 18,
                    balance = dashboard.dashboardInfo.balance.toBigInteger(),
                )

                val tokenBalances = dashboard.layer2Dto?.ethTokens?.map {
                    Balance(
                        address = it.tokenAddress,
                        decimal = it.tokenDecimals,
                        balance = it.balance.toBigInteger(),
                    )
                }
                listOf(coinBalance) + (tokenBalances ?: emptyList())
            } ?: emptyList()
            emit(result)
        }.catch { emptyList<Balance>() }
    }

    override fun loadBalance(account: String): Flow<String> {
        return flow {
            val dashboard = blockchairApi.getDashboardByAccount("ethereum", account)
            emit(dashboard?.dashboardInfo?.balance ?: "0.00")
        }
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

    override suspend fun signTransaction(
        chainId: String,
        privateKey: ByteArray,
        toAddress: String,
        contractAddress: String?,
        amount: String,
        feeLevel: FeeLevel
    ): String = withContext(Dispatchers.IO) {
        val gasLimit = async { lastGasLimit() }.await()
        val nonce = async {
            fetchingNonce()
        }.await()
        val (maxFeePerGas, inclusionFeePerGas) = async {
            calculateFee()
        }.await()

        val isEthNormalTransfer = contractAddress.isNullOrBlank()
        val signingInput = SigningInput(
            private_key = ByteString.of(*privateKey),
            to_address = if (isEthNormalTransfer) toAddress else contractAddress!!,
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

    private suspend fun calculateFee(): Pair<String, String> {
        return Pair("0xB2D05E00", "0x77359400")
    }

    private suspend fun lastGasLimit(): String {
        return "0x0130B9"
    }

    private suspend fun fetchingNonce(): String {
        return "0x0"
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
