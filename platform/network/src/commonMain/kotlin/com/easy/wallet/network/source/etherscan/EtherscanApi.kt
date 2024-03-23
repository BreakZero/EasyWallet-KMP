package com.easy.wallet.network.source.etherscan

import com.easy.wallet.network.source.etherscan.dto.EtherTransactionDto

interface EtherscanApi {
    suspend fun getTransactions(page: Int, offset: Int, account: String): List<EtherTransactionDto>

    suspend fun getContractInternalTransactions(page: Int, offset: Int, account: String, contract: String): List<EtherTransactionDto>
}
