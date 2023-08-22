package com.easy.wallet.home

internal sealed interface HomeEvent {
    data object ShowCreateWalletSheet: HomeEvent
    data object ShowRestoreWalletSheet: HomeEvent
    data object CloseActionSheet: HomeEvent
    data object OnCreateWallet: HomeEvent
    data object OnRestoreWallet: HomeEvent
}

internal sealed interface HomeUiEvent {
    data object OnCreateWallet: HomeUiEvent
    data object OnRestoreWallet: HomeUiEvent
}