package com.easy.wallet.token_manager.token.editor

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState

@OptIn(ExperimentalFoundationApi::class)
data class TokenEditorUiState(
    val name: TextFieldState = TextFieldState()
)

sealed interface TokenEditorEvent {
    data object ClickSaved: TokenEditorEvent
}
