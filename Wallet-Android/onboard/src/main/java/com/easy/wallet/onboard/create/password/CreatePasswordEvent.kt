package com.easy.wallet.onboard.create.password

sealed interface CreatePasswordEvent {
    data class OnPasswordChanged(val password: String): CreatePasswordEvent
    data class OnConfirmPasswordChanged(val confirm: String): CreatePasswordEvent
    data class OnCheckedTermOfService(val checked: Boolean): CreatePasswordEvent

    data object OnViewTermOfService: CreatePasswordEvent
    data object HideTermOfService: CreatePasswordEvent
    data object OnNext: CreatePasswordEvent
}

sealed interface CreatePasswordUiEvent {}