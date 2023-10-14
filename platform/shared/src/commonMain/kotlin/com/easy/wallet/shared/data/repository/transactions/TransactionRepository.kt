package com.easy.wallet.shared.data.repository.transactions

import com.easy.wallet.model.data.Transaction

interface TransactionRepository {
    suspend fun getTransactions(page: Int, offset: Int, account: String): List<Transaction>
}
