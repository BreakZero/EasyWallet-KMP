package com.easy.wallet.shared.data.repository

import com.easy.wallet.network.source.blockchair.BlockchairApi
import com.easy.wallet.shared.model.Balance
import com.ionspin.kotlin.bignum.integer.toBigInteger
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class EthereumRepository internal constructor(
    private val blockchairApi: BlockchairApi
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

    override fun loadTransactions(limit: Int, offset: Int): Flow<Unit> {
        TODO("Not yet implemented")
    }
}
