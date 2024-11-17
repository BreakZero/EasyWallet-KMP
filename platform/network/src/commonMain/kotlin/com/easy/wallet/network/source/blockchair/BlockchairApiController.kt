package com.easy.wallet.network.source.blockchair

import com.easy.wallet.model.news.News
import com.easy.wallet.network.source.blockchair.dto.BlockChairNewsRootResponse
import com.easy.wallet.network.source.blockchair.dto.DashboardRootResponse
import com.easy.wallet.network.tryGet
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter

class BlockchairApiController internal constructor(
  private val httpClient: HttpClient
) : BlockchairApi {
  override suspend fun getNewsList(limit: Int, offset: Int): List<News> = httpClient
    .tryGet<BlockChairNewsRootResponse>("news?q=language(en)") {
      parameter("limit", limit)
      parameter("offset", offset)
    }?.data
    ?.map {
      News(
        title = it.title,
        source = it.source,
        language = it.language,
        link = it.link,
        time = it.time,
        hash = it.hash,
        description = it.description,
        tags = it.tags
      )
    } ?: emptyList()

  override suspend fun getDashboardByAccount(chain: String, account: String): String = httpClient
    .tryGet<DashboardRootResponse>("$chain/dashboards/address/$account") {
      parameter("erc_20", true)
      parameter("limit", 0)
      parameter("nonce", true)
    }?.data
    ?.values
    ?.first()
    ?.toString()
    .orEmpty()
}
