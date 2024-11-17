package com.easy.wallet.marketplace

import com.easy.wallet.model.CoinInformation
import com.easy.wallet.model.CoinMarketInformation

internal sealed interface MarketplaceUiState {
  data object Loading : MarketplaceUiState

  data object Error : MarketplaceUiState

  data class Success(
    val trends: List<CoinInformation>,
    val topCoins: List<CoinMarketInformation>
  ) : MarketplaceUiState
}

internal sealed interface MarketplaceEvent
