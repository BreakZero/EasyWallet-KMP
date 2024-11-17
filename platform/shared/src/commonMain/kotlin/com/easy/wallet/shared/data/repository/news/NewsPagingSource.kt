package com.easy.wallet.shared.data.repository.news

import androidx.paging.PagingState
import app.cash.paging.PagingSource
import com.easy.wallet.model.news.News

internal const val NEWS_PAGER_LIMIT = 30

internal class NewsPagingSource(
  private val newsRepository: NewsRepository
) : PagingSource<Int, News>() {
  override fun getRefreshKey(state: PagingState<Int, News>): Int? = state.anchorPosition?.let { anchorPosition ->
    state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
      ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
  }

  override suspend fun load(params: LoadParams<Int>): LoadResult<Int, News> = try {
    val currentOffset = params.key ?: 0
    val news = newsRepository.loadNews(NEWS_PAGER_LIMIT, currentOffset)
    LoadResult.Page(
      data = news,
      prevKey = if (currentOffset <= 0) null else currentOffset - NEWS_PAGER_LIMIT,
      nextKey = if (news.isEmpty()) null else currentOffset + NEWS_PAGER_LIMIT
    )
  } catch (e: Exception) {
    LoadResult.Error(e)
  }
}
