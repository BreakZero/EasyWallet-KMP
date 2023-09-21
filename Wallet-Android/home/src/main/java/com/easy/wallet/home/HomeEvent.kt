package com.easy.wallet.home

import com.easy.wallet.model.token.Token

internal sealed interface HomeEvent {
    data object ShowCreateWalletSheet : HomeEvent
    data object ShowRestoreWalletSheet : HomeEvent
    data object CloseActionSheet : HomeEvent
    data object OnCreateWallet : HomeEvent
    data object OnRestoreWallet : HomeEvent
    data object ClickSettings : HomeEvent

    data class ClickToken(val token: Token) : HomeEvent
}
