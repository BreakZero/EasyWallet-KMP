package com.easy.wallet.discover

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.easy.wallet.data.news.NewsRepository
import com.easy.wallet.model.news.New

class NewsPagingSource(
    private val newsRepository: NewsRepository
) : PagingSource<Int, New>() {
    private val LIMIT = 20
    override fun getRefreshKey(state: PagingState<Int, New>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, New> {
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