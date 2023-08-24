package com.easy.wallet.onboard.create


sealed interface CreateWalletEvent {
    // Create Start
    data class OnPasswordChanged(val password: String): CreateWalletEvent
    data class OnConfirmPasswordChanged(val confirm: String): CreateWalletEvent
    data class OnCheckedTermOfService(val checked: Boolean): CreateWalletEvent

    data object OnViewTermOfService: CreateWalletEvent
    data object HideTermOfService: CreateWalletEvent
    data object OnNext: CreateWalletEvent
    // Create Ended
}

sealed interface CreateWalletUiEvent {

}
