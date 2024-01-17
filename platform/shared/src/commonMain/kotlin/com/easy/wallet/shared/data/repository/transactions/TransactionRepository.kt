package com.easy.wallet.shared.data.repository.transactions

import com.easy.wallet.model.data.Transaction
import com.easy.wallet.model.token.Token

interface TransactionRepository {
    suspend fun getTransactions(token: Token?, page: Int, offset: Int, account: String): List<Transaction>
}
