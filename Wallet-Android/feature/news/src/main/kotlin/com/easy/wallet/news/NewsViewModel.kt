package com.easy.wallet.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.easy.wallet.android.core.BaseViewModel
import com.easy.wallet.shared.data.repository.news.NewsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn

internal class NewsViewModel(
    newsRepository: NewsRepository
) : ViewModel() {
    private val pageFlow = Pager(
        config = PagingConfig(pageSize = Int.MAX_VALUE, prefetchDistance = 2),
        pagingSourceFactory = {
            NewsPagingSource(newsRepository)
        },
    ).flow

    val newsUiState = pageFlow.distinctUntilChanged().cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())
}
