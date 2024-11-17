package com.easy.wallet.shared.data.repository.platform

import com.easy.wallet.model.asset.AssetCoin
import com.easy.wallet.model.asset.AssetPlatform
import com.easy.wallet.shared.model.fees.FeeModel
import com.easy.wallet.shared.model.transaction.TransactionUiModel

interface OnChainRepository {
  suspend fun loadBalance(
    platform: AssetPlatform,
    account: String,
    contract: String?
  ): String

  suspend fun loadTransactions(
    coin: AssetCoin,
    page: Int,
    offset: Int,
  ): List<TransactionUiModel>

  suspend fun prepFees(
    coin: AssetCoin,
    toAddress: String,
    amount: String
  ): List<FeeModel>

  suspend fun signAndBroadcast(
    coin: AssetCoin,
    toAddress: String,
    amount: String,
    fee: FeeModel
  ): String
}
