package com.easy.wallet.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.easy.wallet.shared.domain.NewsPagerUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn

internal class NewsViewModel(
    newsPagerUseCase: NewsPagerUseCase
) : ViewModel() {
    val newsUiState = newsPagerUseCase().flow.distinctUntilChanged().cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())
}
