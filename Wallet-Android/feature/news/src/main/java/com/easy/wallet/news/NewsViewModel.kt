package com.easy.wallet.news

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.easy.wallet.android.core.BaseViewModel
import com.easy.wallet.model.news.News
import com.easy.wallet.shared.data.repository.news.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

internal class NewsViewModel(
    newsRepository: NewsRepository
) : BaseViewModel<NewsEvent>() {
    private val pageFlow = Pager(
        config = PagingConfig(pageSize = Int.MAX_VALUE, prefetchDistance = 2),
        pagingSourceFactory = {
            NewsPagingSource(newsRepository)
        },
    ).flow

    private val _newsUiState = MutableStateFlow<PagingData<News>>(PagingData.empty())
    val newsUiState = _newsUiState.asStateFlow()

    override fun handleEvent(event: NewsEvent) {
        when (event) {
            is NewsEvent.ClickItem -> dispatchEvent(event)
            NewsEvent.CloseNews -> Unit
        }
    }

    init {
        viewModelScope.launch {
            pageFlow.distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    _newsUiState.value = it
                }
        }
    }
}
