package com.easy.wallet.shared.data.repository.news

import androidx.paging.PagingState
import app.cash.paging.PagingSource
import com.easy.wallet.model.news.News

private const val LIMIT = 20

internal class NewsPagingSource(
    private val newsRepository: NewsRepository
): PagingSource<Int, News>() {
    override fun getRefreshKey(state: PagingState<Int, News>): Int? {
        return state.anchorPosition ?: 1
    }
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> {
        return try {
            val currentOffset = params.key ?: 0
            val news = newsRepository.loadNews(LIMIT, currentOffset)
            LoadResult.Page(
                data = news,
                prevKey = if (currentOffset == 0) null else currentOffset - LIMIT,
                nextKey = if (news.isEmpty()) null else currentOffset + LIMIT,
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
