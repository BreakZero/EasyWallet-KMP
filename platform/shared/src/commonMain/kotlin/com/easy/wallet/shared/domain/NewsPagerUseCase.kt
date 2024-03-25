package com.easy.wallet.shared.domain

import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.easy.wallet.model.news.News
import com.easy.wallet.shared.data.repository.news.NewsPagingSource
import com.easy.wallet.shared.data.repository.news.NewsRepository

class NewsPagerUseCase internal constructor(
    private val newsRepository: NewsRepository
) {
    operator fun invoke(): Pager<Int, News> {
        return Pager<Int, News>(
            config = PagingConfig(pageSize = Int.MAX_VALUE, prefetchDistance = 2),
            pagingSourceFactory = {
                NewsPagingSource(newsRepository)
            }
        )
    }
}
