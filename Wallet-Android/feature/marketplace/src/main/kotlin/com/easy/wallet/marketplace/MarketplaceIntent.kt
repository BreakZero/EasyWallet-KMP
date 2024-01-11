package com.easy.wallet.marketplace

import com.easy.wallet.shared.model.MarketCoin
import com.easy.wallet.shared.model.CoinTrend

internal sealed interface MarketplaceUiState {
    data object Loading: MarketplaceUiState

    data object Error: MarketplaceUiState

    data class Success(
        val trends: List<CoinTrend>,
        val topCoins: List<MarketCoin>
    ): MarketplaceUiState
}

internal sealed interface MarketplaceEvent {}
