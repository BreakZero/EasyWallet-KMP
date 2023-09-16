package com.easy.wallet.data.repository

import com.easy.wallet.data.model.Balance
import com.easy.wallet.data.model.ExtraToken
import kotlinx.coroutines.flow.Flow

interface TokenRepository {

    fun dashboard(account: String): Flow<List<Balance>>
    fun loadBalance(account: String): Flow<String>

    fun loadTransactions(limit: Int, offset: Int): Flow<Unit>
}
