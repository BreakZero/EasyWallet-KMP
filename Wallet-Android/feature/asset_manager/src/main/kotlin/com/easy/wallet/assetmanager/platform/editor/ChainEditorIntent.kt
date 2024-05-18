package com.easy.wallet.assetmanager.platform.editor

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.Stable

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

    data object NavigateUp: ChainEditorUiEvent
}
