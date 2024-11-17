package com.easy.wallet.shared.data.repository.news

import com.easy.wallet.model.news.News
import com.easy.wallet.network.source.blockchair.BlockchairApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class NewsRepository(
  private val blockchairApi: BlockchairApi
) {
  fun loadNewsStream(limit: Int, offset: Int): Flow<List<News>> = flow {
    emit(loadNews(limit, offset))
  }

  suspend fun loadNews(limit: Int, offset: Int): List<News> = blockchairApi.getNewsList(limit, offset)
}
