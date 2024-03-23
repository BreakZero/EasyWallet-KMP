package com.easy.wallet.network.source.blockchair

import com.easy.wallet.network.tryGet
import com.easy.wallet.network.source.blockchair.dto.BlockChairNewDto
import com.easy.wallet.network.source.blockchair.dto.BlockChairNewsRootResponse
import com.easy.wallet.network.source.blockchair.dto.DashboardResponse
import com.easy.wallet.network.source.blockchair.dto.DashboardRootResponse
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter

class BlockchairDataSource internal constructor(
    private val httpClient: HttpClient
) : BlockchairApi {
    override suspend fun getNewsList(limit: Int, offset: Int): List<BlockChairNewDto> {
        return httpClient.tryGet<BlockChairNewsRootResponse>("news?q=language(en)") {
            parameter("limit", limit)
            parameter("offset", offset)
        }?.data ?: emptyList()
    }

    override suspend fun getDashboardByAccount(chain: String, account: String): DashboardResponse? {
        return httpClient.tryGet<DashboardRootResponse>("$chain/dashboards/address/$account") {
            parameter("erc_20", true)
            parameter("limit", 0)
            parameter("nonce", true)
        }?.data?.values?.first()
    }
}
