package com.easy.wallet.network.source.etherscan

import com.easy.wallet.model.transaction.EthereumTransactionBasic
import com.easy.wallet.network.source.etherscan.dto.EtherTransactionDto
import com.easy.wallet.network.source.etherscan.dto.EtherTransactionsResponseDto
import com.easy.wallet.network.tryGet
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter

class EtherscanApiController internal constructor(
    private val httpClient: HttpClient
) : EtherscanApi {
    override suspend fun getTransactions(
        page: Int,
        offset: Int,
        account: String
    ): List<EthereumTransactionBasic> {
        return httpClient.tryGet<EtherTransactionsResponseDto>("") {
            parameter("module", "account")
            parameter("action", "txlist")
            parameter("address", account)
            parameter("page", page)
            parameter("offset", offset)
            parameter("sort", "desc")
        }?.result?.map { it.basic() } ?: emptyList()
    }

    override suspend fun getContractInternalTransactions(
        page: Int,
        offset: Int,
        account: String,
        contract: String
    ): List<EthereumTransactionBasic> {
        return httpClient.tryGet<EtherTransactionsResponseDto>("") {
            parameter("module", "account")
            parameter("action", "tokentx")
            parameter("address", account)
            parameter("page", page)
            parameter("offset", offset)
            parameter("sort", "desc")
            parameter("contractaddress", contract)
        }?.result?.map { it.basic() } ?: emptyList()
    }
}

private fun EtherTransactionDto.basic(): EthereumTransactionBasic {
    return EthereumTransactionBasic(
        hash = hash,
        amount = value,
        recipient = to,
        sender = from,
        gasPrice = gasPrice,
        gas = gas,
        gasUsed = gasUsed,
        functionName = functionName,
        datetime = timeStamp
    )
}
