package com.easy.wallet.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BitcoinRepository: TokenRepository {
    override fun loadBalance(): Flow<String> {
        return flow {
            emit("Bitcoin Balances")
        }
    }

    override fun loadTransactions(limit: Int, offset: Int): Flow<Unit> {
        TODO("Not yet implemented")
    }
}
