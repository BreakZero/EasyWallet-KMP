package com.easy.wallet.data.news

import com.easy.wallet.data.news.mapper.externalModel
import com.easy.wallet.data.source.blockchair.BlockchairApi
import com.easy.wallet.data.source.blockchair.model.BlockChairNewDto
import com.easy.wallet.model.news.News
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class NewsRepository internal constructor(
    private val blockchairApi: BlockchairApi
) {
    fun loadNewsStream(limit: Int, offset: Int): Flow<List<News>> {
        return flow {
            emit(loadNews(limit, offset))
        }
    }

    suspend fun loadNews(limit: Int, offset: Int): List<News> {
        return blockchairApi.getNewsList(limit, offset).map(BlockChairNewDto::externalModel)
    }
}
