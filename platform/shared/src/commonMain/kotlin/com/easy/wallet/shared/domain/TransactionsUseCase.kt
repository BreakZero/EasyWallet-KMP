package com.easy.wallet.shared.domain

import com.easy.wallet.model.data.Transaction
import com.easy.wallet.shared.data.repository.transactions.TransactionRepository

class TransactionsUseCase internal constructor(
    private val ethereumTransactionRepository: TransactionRepository
) {
    suspend operator fun invoke(
        chain: String,
        page: Int,
        offset: Int,
        account: String
    ): List<Transaction> {
        return ethereumTransactionRepository.getTransactions(page, offset, account)
    }
}
