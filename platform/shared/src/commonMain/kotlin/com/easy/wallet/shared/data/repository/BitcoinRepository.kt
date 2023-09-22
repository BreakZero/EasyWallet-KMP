package com.easy.wallet.shared.data.repository

import com.easy.wallet.shared.model.Balance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BitcoinRepository : TokenRepository {
    override fun dashboard(account: String): Flow<List<Balance>> {
        println("===== Bitcoin Address $account")
        return flow {
            emit(emptyList())
        }
    }

    override fun loadBalance(account: String): Flow<String> {
        return flow {
            emit("Bitcoin Balances")
        }
    }

    override fun loadTransactions(limit: Int, offset: Int): Flow<Unit> {
        TODO("Not yet implemented")
    }
}
