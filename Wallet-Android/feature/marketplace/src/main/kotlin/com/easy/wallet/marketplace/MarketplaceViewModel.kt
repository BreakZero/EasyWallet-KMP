package com.easy.wallet.marketplace

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.easy.wallet.shared.data.repository.MarketsRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn

class MarketplaceViewModel(
    marketsRepository: MarketsRepository
) : ViewModel() {

    private val _topCoinsPagingFlow = marketsRepository.topCoinsByPagingFlow()
    private val _trends = marketsRepository.searchTrends()

    internal val topCoins = _topCoinsPagingFlow.distinctUntilChanged().cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(3_000), PagingData.empty())

    internal val trendingCoins = _trends.stateIn(viewModelScope, SharingStarted.WhileSubscribed(3_000), emptyList())
}
