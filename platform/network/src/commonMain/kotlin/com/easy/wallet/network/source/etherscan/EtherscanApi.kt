package com.easy.wallet.network.source.etherscan

import com.easy.wallet.model.transaction.EthereumTransactionBasic

interface EtherscanApi {
  suspend fun getTransactions(
    page: Int,
    offset: Int,
    account: String
  ): List<EthereumTransactionBasic>

  suspend fun getContractInternalTransactions(
    page: Int,
    offset: Int,
    account: String,
    contract: String
  ): List<EthereumTransactionBasic>
}
