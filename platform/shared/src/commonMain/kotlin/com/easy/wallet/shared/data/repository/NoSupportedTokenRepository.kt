package com.easy.wallet.shared.data.repository

import com.easy.wallet.model.TokenBasicResult
import com.easy.wallet.shared.model.Balance
import com.easy.wallet.shared.model.transaction.TransactionUiModel
import kotlinx.coroutines.flow.Flow

class NoSupportedTokenRepository internal constructor() : TokenRepository {
    override fun dashboard(account: String): Flow<List<Balance>> {
        throw NoSuchElementException("token not supported yet!!!")
    }

    override fun loadBalance(account: String): Flow<String> {
        throw NoSuchElementException("token not supported yet!!!")
    }

    override suspend fun loadTransactions(
        token: TokenBasicResult,
        page: Int,
        offset: Int
    ): List<TransactionUiModel> {
        throw NoSuchElementException("token not supported yet!!!")
    }
}
