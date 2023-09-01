package com.easy.wallet.data.news

import com.easy.wallet.data.news.model.BlockChairBaseResponse
import com.easy.wallet.data.news.model.BlockChairNewDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

internal class NewsApi(
    private val client: HttpClient
) {
    suspend fun loadNews(limit: Int, offset: Int): List<BlockChairNewDto> {
        val resp = client.get("news").body<BlockChairBaseResponse<List<BlockChairNewDto>>>()
        println("===== ${resp.data.size}")
        return resp.data
    }
}