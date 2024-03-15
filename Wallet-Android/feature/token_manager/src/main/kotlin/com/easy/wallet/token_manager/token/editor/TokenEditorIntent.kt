package com.easy.wallet.token_manager.token.editor

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.runtime.Stable
import com.easy.wallet.model.ChainInformation

@OptIn(ExperimentalFoundationApi::class)
data class TokenEditorUiState(
    val name: TextFieldState = TextFieldState(),
    val symbol: TextFieldState = TextFieldState(),
    val decimals: TextFieldState = TextFieldState(),
    val contract: TextFieldState = TextFieldState(),
    val iconUri: TextFieldState = TextFieldState(),
    val localChains: List<ChainInformation>,
    val chainId: Long,
    val isActive: Boolean = false
)

sealed interface TokenEditorEvent {
    data object ClickSaved: TokenEditorEvent
}
