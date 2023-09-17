package com.easy.wallet.data.repository

import com.easy.wallet.data.model.Balance
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BitcoinRepository : TokenRepository {
    override fun dashboard(account: String): Flow<List<Balance>> {
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
