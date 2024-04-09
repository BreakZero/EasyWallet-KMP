package com.easy.wallet.shared.data.repository.news

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.easy.wallet.model.news.News
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow

class NewsPager internal constructor(
    private val newsRepository: NewsRepository
) {
    @NativeCoroutines
    operator fun invoke(): Flow<PagingData<News>> {
        return Pager(
            config = PagingConfig(pageSize = NEWS_PAGER_LIMIT, prefetchDistance = 2),
            pagingSourceFactory = {
                NewsPagingSource(newsRepository)
            }
        ).flow
    }
}
