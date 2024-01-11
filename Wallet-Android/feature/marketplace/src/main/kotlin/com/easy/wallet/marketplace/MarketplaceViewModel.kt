package com.easy.wallet.marketplace

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easy.wallet.network.source.okx.dto.OptionArg
import com.easy.wallet.shared.data.okx.OKXDataRepository
import com.easy.wallet.shared.data.repository.MarketsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MarketplaceViewModel(
    private val marketsRepository: MarketsRepository
) : ViewModel() {

    private val _topCoins = marketsRepository.fetchTopCoins()
    private val _trends = marketsRepository.searchTrends()

    internal val marketplaceUiState = combine(_topCoins, _trends) { topCoins, trends ->
        MarketplaceUiState.Success(trends = trends, topCoins = topCoins)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(3_000), MarketplaceUiState.Loading)
}
