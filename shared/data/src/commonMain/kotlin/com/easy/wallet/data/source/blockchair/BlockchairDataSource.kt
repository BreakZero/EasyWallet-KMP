package com.easy.wallet.data.source.blockchair

import com.easy.wallet.data.source.blockchair.model.BlockChairBaseResponse
import com.easy.wallet.data.source.blockchair.model.BlockChairNewDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.request.parameter

internal class BlockchairDataSource constructor(
    private val httpClient: HttpClient
) : BlockchairApi {
    override suspend fun getNewsList(limit: Int, offset: Int): List<BlockChairNewDto> {
        val resp = httpClient.get("news?q=language(en)") {
            parameter("limit", limit)
            parameter("offset", offset)
        }.body<BlockChairBaseResponse<List<BlockChairNewDto>>>()
        return resp.data
    }

}
