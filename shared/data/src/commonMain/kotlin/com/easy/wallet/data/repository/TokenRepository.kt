package com.easy.wallet.data.repository

import kotlinx.coroutines.flow.Flow

interface TokenRepository {
    fun loadBalance(): Flow<String>

    fun loadTransactions(limit: Int, offset: Int): Flow<Unit>
}
