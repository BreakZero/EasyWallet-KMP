package com.easy.wallet.send

sealed interface SendUiEvent {
    data object Backspace: SendUiEvent
    data class EnterDigital(val char: String): SendUiEvent
    data object MaxAmount: SendUiEvent
    data object ClickNext: SendUiEvent
}
