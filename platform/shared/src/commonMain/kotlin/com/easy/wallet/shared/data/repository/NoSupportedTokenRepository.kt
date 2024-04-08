package com.easy.wallet.shared.data.repository

import com.easy.wallet.model.TokenBasicResult
import com.easy.wallet.shared.model.fees.FeeModel
import com.easy.wallet.shared.model.transaction.TransactionUiModel

class NoSupportedTokenRepository internal constructor() : TokenRepository {
    override suspend fun loadBalance(account: String, contract: String?): String {
        TODO("token not supported yet!!!")
    }

    override suspend fun loadTransactions(
        token: TokenBasicResult,
        page: Int,
        offset: Int
    ): List<TransactionUiModel> {
        TODO("token not supported yet!!!")
    }

    override suspend fun prepFees(
        account: String,
        toAddress: String,
        contractAddress: String?,
        amount: String
    ): List<FeeModel>  {
        TODO("Not yet implemented")
    }

    override suspend fun signTransaction(
        account: String,
        chainId: String,
        privateKey: ByteArray,
        toAddress: String,
        contractAddress: String?,
        amount: String,
        fee: FeeModel
    ): String {
        TODO("Not yet implemented")
    }
}
