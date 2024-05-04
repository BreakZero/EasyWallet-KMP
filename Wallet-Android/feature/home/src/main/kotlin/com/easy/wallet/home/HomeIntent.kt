package com.easy.wallet.home

import androidx.compose.runtime.Stable
import com.easy.wallet.model.asset.CoinModel
import com.easy.wallet.shared.model.AllAssetDashboardInformation

internal sealed interface HomeEvent {
    data object CreateWallet : HomeEvent
    data object RestoreWallet : HomeEvent
    data object SettingsClicked : HomeEvent
    data class OnCoinClicked(val coin: CoinModel) : HomeEvent

    data object OnRefreshing : HomeEvent
}


internal sealed interface HomeUiState {
    data object Initial: HomeUiState
    data object GuestUserUiState : HomeUiState

    @Stable
    data class WalletUiState(
        val dashboard: AllAssetDashboardInformation
    ) : HomeUiState
}
