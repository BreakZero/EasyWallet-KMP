package com.easy.wallet.shared.data.repository

import com.easy.wallet.core.commom.DateTimeDecoder
import com.easy.wallet.model.TokenInformation
import com.easy.wallet.network.source.blockchair.BlockchairApi
import com.easy.wallet.network.source.etherscan.EtherscanApi
import com.easy.wallet.network.source.etherscan.dto.EtherTransactionDto
import com.easy.wallet.shared.model.Balance
import com.easy.wallet.shared.model.transaction.Direction
import com.easy.wallet.shared.model.transaction.EthereumTransactionUiModel
import com.easy.wallet.shared.model.transaction.TransactionStatus
import com.easy.wallet.shared.model.transaction.TransactionUiModel
import com.ionspin.kotlin.bignum.decimal.RoundingMode
import com.ionspin.kotlin.bignum.decimal.toBigDecimal
import com.ionspin.kotlin.bignum.integer.toBigInteger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class EthereumRepository internal constructor(
    private val blockchairApi: BlockchairApi,
    private val etherscanApi: EtherscanApi
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
        account: String,
        token: TokenInformation,
        page: Int,
        offset: Int
    ): List<TransactionUiModel> {
        val isContract = !token.contract.isNullOrBlank()
        val tnxDto = if (isContract) {
            etherscanApi.getContractInternalTransactions(page, offset, account, token.contract.orEmpty())
        } else {
            etherscanApi.getTransactions(page, offset, account)
        }
        return tnxDto.map { it.asTransactionUiModel(token, account) }
    }
}

private fun EtherTransactionDto.asTransactionUiModel(
    token: TokenInformation,
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
        datetime = timeStamp.toLongOrNull()?.let { DateTimeDecoder.decodeToDateTime(it.times(1000)).toString() } ?: "- -",
        status = TransactionStatus.Confirmed
    )
}
