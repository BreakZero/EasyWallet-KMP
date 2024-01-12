package com.easy.wallet.shared.data.repository

import com.easy.wallet.model.enums.CoinVals
import com.easy.wallet.network.source.blockchair.BlockchairApi
import com.easy.wallet.shared.model.Balance
import com.ionspin.kotlin.bignum.integer.toBigInteger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EthereumRepository internal constructor(
    private val blockchairApi: BlockchairApi
) : TokenRepository {
    override fun dashboard(account: String): Flow<List<Balance>> {
//        println("===== Ethereum Address $account")
        return flow {
            val dashboard = blockchairApi.getDashboardByAccount("ethereum", account)

            val coinBalance = Balance(
                type = CoinVals.CoinType.COIN,
                address = "ethereum",
                decimal = 18,
                balance = dashboard.dashboardInfo.balance.toBigInteger(),
            )

            val tokenBalances = dashboard.layer2Dto?.ethTokens?.map {
                Balance(
                    type = CoinVals.CoinType.ERC20,
                    address = it.tokenAddress,
                    decimal = it.tokenDecimals,
                    balance = it.balance.toBigInteger(),
                )
            }
            val result = tokenBalances?.let {
                tokenBalances + coinBalance
            } ?: listOf(coinBalance)
            emit(result)
        }
    }

    override fun loadBalance(account: String): Flow<String> {
        return flow {
            val dashboard = blockchairApi.getDashboardByAccount("ethereum", account)
            emit(dashboard.dashboardInfo.balance)
        }
    }

    override fun loadTransactions(limit: Int, offset: Int): Flow<Unit> {
        TODO("Not yet implemented")
    }
}
