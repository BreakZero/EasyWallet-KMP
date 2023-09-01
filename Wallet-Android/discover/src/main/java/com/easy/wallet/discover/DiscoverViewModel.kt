package com.easy.wallet.discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.easy.wallet.core.result.Result
import com.easy.wallet.core.result.asResult
import com.easy.wallet.data.news.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DiscoverViewModel(
    newsRepository: NewsRepository
) : ViewModel() {
    private val _discoverUiState = MutableStateFlow(DiscoverUiState.Loading)
    private val pageFlow = Pager(
        config = PagingConfig(pageSize = Int.MAX_VALUE, prefetchDistance = 2),
        pagingSourceFactory = {
            NewsPagingSource(newsRepository)
        }
    ).flow

    init {
        viewModelScope.launch {
            pageFlow.distinctUntilChanged()
                .cachedIn(viewModelScope)
                .collect {
                    
                }
        }
    }

    val discoverUState: StateFlow<DiscoverUiState> = newsRepository.loadNewsStream(10, 0)
        .asResult()
        .map { result ->
            when (result) {
                is Result.Loading -> DiscoverUiState.Loading
                is Result.Error -> DiscoverUiState.Error(result.exception?.message.orEmpty())
                is Result.Success -> DiscoverUiState.Success(result.data)
            }
        }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), DiscoverUiState.Loading)
}