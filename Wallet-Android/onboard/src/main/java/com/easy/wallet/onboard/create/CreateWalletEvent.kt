package com.easy.wallet.onboard.create

sealed interface CreateWalletEvent {
    data object Close : CreateWalletEvent

    // Create Start
    data class OnPasswordChanged(val password: String) : CreateWalletEvent
    data class OnConfirmPasswordChanged(val confirm: String) : CreateWalletEvent
    data class OnCheckedTermOfServiceChanged(val checked: Boolean) : CreateWalletEvent

    data object OnViewTermOfService : CreateWalletEvent
    data object HideTermOfService : CreateWalletEvent
    data object NextToSecure : CreateWalletEvent
    // Create Ended

    data object NextToCheckSeed : CreateWalletEvent
    data object OnCreateWallet: CreateWalletEvent
}
