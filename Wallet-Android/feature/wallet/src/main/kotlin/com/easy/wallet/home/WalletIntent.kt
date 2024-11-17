package com.easy.wallet.home

import androidx.compose.runtime.Stable
import com.easy.wallet.model.asset.CoinModel
import com.easy.wallet.shared.model.AllAssetDashboardInformation

internal sealed interface WalletEvent {
  data object CreateWallet : WalletEvent

  data object RestoreWallet : WalletEvent

  data object SettingsClicked : WalletEvent

  data class OnCoinClicked(
    val coin: CoinModel
  ) : WalletEvent

  data object OnRefreshing : WalletEvent
}

internal sealed interface WalletUiState {
  data object Initial : com.easy.wallet.home.WalletUiState

  data object GuestUserUiState : com.easy.wallet.home.WalletUiState

  @Stable
  data class WalletUiState(
    val dashboard: AllAssetDashboardInformation
  ) : com.easy.wallet.home.WalletUiState
}
