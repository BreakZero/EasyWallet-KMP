package com.easy.wallet.discover

import com.easy.wallet.model.news.New


sealed interface DiscoverUiState {
    data object Loading: DiscoverUiState
    data class Error(val error: String): DiscoverUiState

    data class Success(val news: List<New>): DiscoverUiState
}

