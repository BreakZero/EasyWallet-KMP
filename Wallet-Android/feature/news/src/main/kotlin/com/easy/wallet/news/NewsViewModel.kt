package com.easy.wallet.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.easy.wallet.shared.data.repository.news.NewsPager
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn

internal class NewsViewModel(
  newsPager: NewsPager
) : ViewModel() {
  val newsUiState = newsPager()
    .distinctUntilChanged()
    .cachedIn(viewModelScope)
    .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), PagingData.empty())
}
