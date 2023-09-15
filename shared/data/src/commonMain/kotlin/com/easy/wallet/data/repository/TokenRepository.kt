package com.easy.wallet.data.repository

import kotlinx.coroutines.flow.Flow

interface TokenRepository {

    fun dashboard(account: String): Flow<Map<String, String>>
    fun loadBalance(account: String): Flow<String>

    fun loadTransactions(limit: Int, offset: Int): Flow<Unit>
}
