package com.easy.wallet.shared.data.repository

import com.easy.wallet.shared.model.Balance
import kotlinx.coroutines.flow.Flow

interface TokenRepository {

    fun dashboard(account: String): Flow<List<Balance>>
    fun loadBalance(account: String): Flow<String>

    fun loadTransactions(limit: Int, offset: Int): Flow<Unit>
}
