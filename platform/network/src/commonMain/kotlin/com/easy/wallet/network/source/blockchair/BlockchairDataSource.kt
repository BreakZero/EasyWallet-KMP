package com.easy.wallet.network.source.blockchair

import com.easy.wallet.network.source.blockchair.model.BlockChairNewDto
import com.easy.wallet.network.source.blockchair.model.BlockChairNewsRootResponse
import com.easy.wallet.network.source.blockchair.model.DashboardResponse
import com.easy.wallet.network.source.blockchair.model.DashboardRootResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

class BlockchairDataSource internal constructor(
    private val httpClient: HttpClient
) : BlockchairApi {
    override suspend fun getNewsList(limit: Int, offset: Int): List<BlockChairNewDto> {
        val resp = httpClient.get("news?q=language(en)") {
            parameter("limit", limit)
            parameter("offset", offset)
        }.body<BlockChairNewsRootResponse>()
        return resp.data
    }

    override suspend fun getDashboardByAccount(chain: String, account: String): DashboardResponse {
        val resp = httpClient.get("$chain/dashboards/address/$account") {
            parameter("erc_20", true)
            parameter("limit", 0)
            parameter("nonce", true)
//            parameter("apiKey", "")
        }.body<DashboardRootResponse>()
        return resp.data.values.first()
    }
}
