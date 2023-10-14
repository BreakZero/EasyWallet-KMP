package com.easy.wallet.shared.data.repository.news

import com.easy.wallet.model.news.News
import com.easy.wallet.network.source.blockchair.BlockchairApi
import com.easy.wallet.network.source.blockchair.dto.BlockChairNewDto
import com.easy.wallet.shared.data.repository.news.mapper.externalModel
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
