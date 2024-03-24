package com.easy.wallet.shared.data.repository

import com.easy.wallet.model.TokenInformation
import com.easy.wallet.model.data.Transaction
import com.easy.wallet.shared.model.Balance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BitcoinRepository : TokenRepository {
    override fun dashboard(account: String): Flow<List<Balance>> {
//        println("===== Bitcoin Address $account")
        return flow {
            emit(emptyList())
        }
    }

    override fun loadBalance(account: String): Flow<String> {
        return flow {
            emit("Bitcoin Balances")
        }
    }

    override suspend fun loadTransactions(
        account: String,
        token: TokenInformation,
        page: Int,
        offset: Int
    ): List<Transaction> {
        TODO("Not yet implemented")
    }
}
