package com.easy.wallet.home

import com.easy.wallet.home.component.ActionSheetMenu
import com.easy.wallet.model.token.Token
import com.easy.wallet.shared.model.ExtraToken

internal sealed interface HomeEvent {
    data object OpenCreateWalletActions: HomeEvent
    data object OpenRestoreWalletActions: HomeEvent
    data object CreateWallet: HomeEvent
    data object RestoreWallet: HomeEvent
    data object SettingsClicked: HomeEvent
    data class TokenClicked(val token: Token): HomeEvent
}

internal sealed interface HomeUiState {
    data object Loading: HomeUiState
    data class GuestUiState(
        val actions: List<ActionSheetMenu> = emptyList()
    ): HomeUiState

    data class WalletUiState(
        val tokens: List<ExtraToken>
    ): HomeUiState
}
