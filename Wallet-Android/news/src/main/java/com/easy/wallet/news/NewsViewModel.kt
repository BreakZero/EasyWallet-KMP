package com.easy.wallet.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.easy.wallet.data.news.NewsRepository
import com.easy.wallet.model.news.News
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class NewsViewModel(
    newsRepository: NewsRepository
): ViewModel() {
    private val pageFlow = Pager(
        config = PagingConfig(pageSize = Int.MAX_VALUE, prefetchDistance = 2),
        pagingSourceFactory = {
            NewsPagingSource(newsRepository)
        }
    ).flow

    private val _newsUiState = MutableStateFlow<PagingData<News>>(PagingData.empty())
    val newsUiState = _newsUiState.asStateFlow()

    init {
        println("===== init")
        viewModelScope.launch {
            pageFlow.distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _newsUiState.value = it
                }
        }
    }
}