package com.easy.wallet.shared.data.repository.chain

import com.easy.wallet.model.asset.AssetCoin
import com.easy.wallet.shared.model.fees.FeeModel
import com.easy.wallet.shared.model.transaction.TransactionUiModel

class NoSupportedChainRepository internal constructor(): OnChainRepository {
    override suspend fun loadBalance(account: String, contract: String?): String {
        TODO("Not yet implemented")
    }

    override suspend fun loadTransactions(
        coin: AssetCoin,
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
    ): List<FeeModel> {
        TODO("Not yet implemented")
    }

    override suspend fun signAndBroadcast(
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
