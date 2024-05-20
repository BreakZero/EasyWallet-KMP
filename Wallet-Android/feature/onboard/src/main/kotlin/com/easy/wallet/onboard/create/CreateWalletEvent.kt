package com.easy.wallet.onboard.create

sealed interface CreateWalletEvent {
    data object PopBack: CreateWalletEvent

    data object OnCreatePassword: CreateWalletEvent

    data object OnCreateWallet : CreateWalletEvent
    data object OnStartInSecure: CreateWalletEvent

    data object NavigateToMnemonic: CreateWalletEvent

    data object NavigateToSecure: CreateWalletEvent
    data object NavigateToWalletTab: CreateWalletEvent
}
