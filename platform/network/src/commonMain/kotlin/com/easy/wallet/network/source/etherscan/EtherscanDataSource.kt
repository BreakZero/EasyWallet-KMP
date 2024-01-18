package com.easy.wallet.network.source.etherscan

import com.easy.wallet.network.doGetWithCatch
import com.easy.wallet.network.source.etherscan.dto.EtherTransactionDto
import com.easy.wallet.network.source.etherscan.dto.EtherTransactionsResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter

class EtherscanDataSource internal constructor(
    private val httpClient: HttpClient
) : EtherscanApi {
    override suspend fun getTransactions(
        page: Int,
        offset: Int,
        account: String
    ): List<EtherTransactionDto> {
        return httpClient.doGetWithCatch<EtherTransactionsResponseDto>("") {
            parameter("module", "account")
            parameter("action", "txlist")
            parameter("address", account)
            parameter("page", page)
            parameter("offset", offset)
            parameter("sort", "asc")
        }?.result ?: emptyList()
    }
}
