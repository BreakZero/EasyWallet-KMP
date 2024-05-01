package com.easy.wallet.send

import androidx.compose.runtime.Stable
import com.easy.wallet.model.TokenBasicResult

sealed interface SendUiEvent {
    data object Backspace : SendUiEvent
    data class EnterDigital(val char: String) : SendUiEvent
    data object MaxAmount : SendUiEvent
    data object ClickNext : SendUiEvent

    data object TransactionPrep: SendUiEvent

    data class OnSigningTransaction(val chainIdHex: String?): SendUiEvent
    data class DestinationChanged(val text: String): SendUiEvent
}

internal sealed interface SendUiState {
    data object Loading: SendUiState

    data object Error: SendUiState

    @Stable
    data class Success(
        val tokenInfo: TokenBasicResult,
        val balance: String,
        val amount: String,
        val recipient: String
    ): SendUiState
}

