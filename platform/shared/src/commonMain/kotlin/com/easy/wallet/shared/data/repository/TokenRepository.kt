package com.easy.wallet.shared.data.repository

import com.easy.wallet.model.TokenBasicResult
import com.easy.wallet.model.TokenInformation
import com.easy.wallet.shared.model.Balance
import com.easy.wallet.shared.model.FeeLevel
import com.easy.wallet.shared.model.transaction.TransactionUiModel
import kotlinx.coroutines.flow.Flow

interface TokenRepository {

    fun dashboard(account: String): Flow<List<Balance>>
    fun loadBalance(account: String): Flow<String>

    suspend fun loadTransactions(
        token: TokenBasicResult,
        page: Int,
        offset: Int,
    ): List<TransactionUiModel>

    suspend fun signTransaction(
        chainId: String,
        privateKey: ByteArray,
        toAddress: String,
        contractAddress: String? = null,
        amount: String,
        feeLevel: FeeLevel
    ): String
}
