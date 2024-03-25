package com.easy.wallet.shared.data.repository.news

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.easy.wallet.model.news.News

class NewsPager internal constructor(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(): Pager<Int, News> {
        return Pager(
            config = PagingConfig(pageSize = NEWS_PAGER_LIMIT, prefetchDistance = 2),
            pagingSourceFactory = {
                NewsPagingSource(newsRepository)
            }
        )
    }
}
