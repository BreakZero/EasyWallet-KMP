package com.easy.wallet.shared.data.repository

import com.easy.wallet.model.TokenBasicResult
import com.easy.wallet.shared.model.Balance
import com.easy.wallet.shared.model.FeeLevel
import com.easy.wallet.shared.model.transaction.TransactionUiModel
import kotlinx.coroutines.flow.Flow

class NoSupportedTokenRepository internal constructor() : TokenRepository {
    override fun dashboard(account: String): Flow<List<Balance>> {
        TODO("token not supported yet!!!")
    }

    override fun loadBalance(account: String): Flow<String> {
        TODO("token not supported yet!!!")
    }

    override suspend fun loadTransactions(
        token: TokenBasicResult,
        page: Int,
        offset: Int
    ): List<TransactionUiModel> {
        TODO("token not supported yet!!!")
    }

    override suspend fun signTransaction(
        account: String,
        chainId: String,
        privateKey: ByteArray,
        toAddress: String,
        contractAddress: String?,
        amount: String,
        feeLevel: FeeLevel
    ): String {
        TODO("Not yet implemented")
    }
}
