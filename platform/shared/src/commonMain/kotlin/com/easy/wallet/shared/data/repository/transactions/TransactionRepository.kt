package com.easy.wallet.shared.data.repository.transactions

import com.easy.wallet.model.TokenInformation
import com.easy.wallet.model.data.Transaction

interface TransactionRepository {
    suspend fun getTransactions(token: TokenInformation?, page: Int, offset: Int, account: String): List<Transaction>
}
