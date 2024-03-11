package com.easy.wallet.token_manager.chain.editor

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.runtime.Stable

@OptIn(ExperimentalFoundationApi::class)
@Stable
internal data class ChainEditorUiState(
    val name: TextFieldState = TextFieldState(""),
    val website: TextFieldState = TextFieldState(""),
    val explorer: TextFieldState = TextFieldState(""),
    val rpcUrl: TextFieldState = TextFieldState(""),
    val chainId: TextFieldState = TextFieldState("")
)

internal sealed interface ChainEditorUiEvent {
    data object OnSavedClick: ChainEditorUiEvent
}
