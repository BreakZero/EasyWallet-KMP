package com.easy.wallet.news

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.easy.wallet.data.news.NewsRepository
import com.easy.wallet.model.news.News

private const val LIMIT = 20
class NewsPagingSource(
    private val newsRepository: NewsRepository
) : PagingSource<Int, News>() {
    override fun getRefreshKey(state: PagingState<Int, News>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
        return try {
            val currentOffset = params.key ?: 0
            val news = newsRepository.loadNews(LIMIT, currentOffset)
            LoadResult.Page(
                data = news,
                prevKey = if (currentOffset == 0) null else currentOffset - LIMIT,
                nextKey = if (news.isEmpty()) null else currentOffset + LIMIT
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}