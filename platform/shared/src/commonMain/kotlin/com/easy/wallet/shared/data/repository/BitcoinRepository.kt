package com.easy.wallet.shared.data.repository

import com.easy.wallet.model.TokenBasicResult
import com.easy.wallet.shared.model.fees.FeeModel
import com.easy.wallet.shared.model.transaction.TransactionUiModel

class BitcoinRepository : TokenRepository {
    override suspend fun loadBalance(account: String, contract: String?): String {
        return "0.00"
    }

    override suspend fun loadTransactions(
        token: TokenBasicResult,
        page: Int,
        offset: Int
    ): List<TransactionUiModel> {
        TODO("Not yet implemented")
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
