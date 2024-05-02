package com.easy.wallet.home

import androidx.compose.runtime.Stable
import com.easy.wallet.model.TokenInformation
import com.easy.wallet.shared.model.DashboardInformation
import com.easy.wallet.shared.model.TokenUiModel

internal sealed interface HomeEvent {
    data object CreateWallet : HomeEvent
    data object RestoreWallet : HomeEvent
    data object SettingsClicked : HomeEvent
    data class TokenClicked(val token: TokenInformation) : HomeEvent

    data object OnRefreshing : HomeEvent
}


internal sealed interface HomeUiState {
    data object Initial: HomeUiState
    data object GuestUserUiState : HomeUiState

    @Stable
    data class WalletUiState(
        val dashboard: DashboardInformation
    ) : HomeUiState
}
