package com.easy.wallet.network.source.etherscan

import com.easy.wallet.network.source.etherscan.dto.EtherTransactionDto
import com.easy.wallet.network.source.etherscan.dto.EtherTransactionsResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class EtherscanDataSource internal constructor(
    private val httpClient: HttpClient
) : EtherscanApi {
    override suspend fun getTransactions(
        page: Int,
        offset: Int,
        account: String
    ): List<EtherTransactionDto> {
        val responseDto = httpClient.get {
            parameter("module", "account")
            parameter("action", "txlist")
            parameter("address", account)
            parameter("page", page)
            parameter("offset", offset)
            parameter("sort", "asc")
        }.body<EtherTransactionsResponseDto>()
        return responseDto.result
    }
}
