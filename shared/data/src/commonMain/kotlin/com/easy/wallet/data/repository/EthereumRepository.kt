package com.easy.wallet.data.repository

import com.easy.wallet.data.source.blockchair.BlockchairApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class EthereumRepository internal constructor(
    private val blockchairApi: BlockchairApi
) : TokenRepository {
    override fun dashboard(account: String): Flow<Map<String, String>> {
        return flow {
            val dashboard = blockchairApi.getDashboardByAccount("ethereum", account)
            val coinBalance = mapOf("" to dashboard.dashboardInfo.balance)
            val tokenBalances = dashboard.layer2Dto?.ethTokens?.map {
                it.tokenAddress.lowercase() to it.balance
            }?.toMap()
            val result = tokenBalances?.let {
                coinBalance + it
            } ?: coinBalance
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
