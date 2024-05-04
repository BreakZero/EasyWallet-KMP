package com.easy.wallet.shared.data.repository.chain

import com.easy.wallet.model.asset.AssetCoin
import com.easy.wallet.model.asset.BasicCoin
import com.easy.wallet.shared.model.fees.FeeModel
import com.easy.wallet.shared.model.transaction.TransactionUiModel

interface OnChainRepository {
    suspend fun loadBalance(account: String, contract: String?): String

    suspend fun loadTransactions(
        coin: AssetCoin,
        page: Int,
        offset: Int,
    ): List<TransactionUiModel>

    suspend fun prepFees(
        account: String,
        toAddress: String,
        contractAddress: String?,
        amount: String
    ): List<FeeModel>

    suspend fun signAndBroadcast(
        account: String,
        chainId: String,
        privateKey: ByteArray,
        toAddress: String,
        contractAddress: String? = null,
        amount: String,
        fee: FeeModel
    ): String
}
