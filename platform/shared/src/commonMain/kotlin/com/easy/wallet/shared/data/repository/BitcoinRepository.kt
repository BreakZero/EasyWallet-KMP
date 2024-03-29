package com.easy.wallet.shared.data.repository

import com.easy.wallet.model.TokenInformation
import com.easy.wallet.shared.model.Balance
import com.easy.wallet.shared.model.transaction.TransactionUiModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class BitcoinRepository : TokenRepository {
    override fun dashboard(account: String): Flow<List<Balance>> {
//        println("===== Bitcoin Address $account")
        return flow {
            emit(emptyList())
        }
    }

    override fun loadBalance(account: String): Flow<String> {
        return flow {
            emit("Bitcoin Balances")
        }
    }

    override suspend fun loadTransactions(
        account: String,
        token: TokenInformation,
        page: Int,
        offset: Int
    ): List<TransactionUiModel> {
        TODO("Not yet implemented")
    }
}
