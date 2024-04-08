package com.easy.wallet.shared.data.repository

import com.easy.wallet.model.TokenBasicResult
import com.easy.wallet.shared.model.Balance
import com.easy.wallet.shared.model.fees.FeeModel
import com.easy.wallet.shared.model.transaction.TransactionUiModel
import kotlinx.coroutines.flow.Flow

interface TokenRepository {

    suspend fun loadBalance(account: String, contract: String?): String

    suspend fun loadTransactions(
        token: TokenBasicResult,
        page: Int,
        offset: Int,
    ): List<TransactionUiModel>

    suspend fun prepFees(
        account: String,
        toAddress: String,
        contractAddress: String?,
        amount: String
    ): List<FeeModel>

    suspend fun signTransaction(
        account: String,
        chainId: String,
        privateKey: ByteArray,
        toAddress: String,
        contractAddress: String? = null,
        amount: String,
        fee: FeeModel
    ): String
}
