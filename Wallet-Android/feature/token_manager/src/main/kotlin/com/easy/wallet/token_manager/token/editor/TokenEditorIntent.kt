package com.easy.wallet.token_manager.token.editor

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.runtime.Stable
import com.easy.wallet.model.ChainInformation

data class TokenEditorUiState(
    val localChains: List<ChainInformation>,
    val chainName: String,
    val isActive: Boolean = false
)

@OptIn(ExperimentalFoundationApi::class)
@Stable
data class TokenEditorFields(
    val name: TextFieldState = TextFieldState(),
    val symbol: TextFieldState = TextFieldState(),
    val decimals: TextFieldState = TextFieldState(),
    val contract: TextFieldState = TextFieldState(),
    val iconUri: TextFieldState = TextFieldState(),
)

sealed interface TokenEditorEvent {
    data class OnChainChanged(val chainId: Long) : TokenEditorEvent

    data class OnActiveChanged(val isActive: Boolean) : TokenEditorEvent
    data object ClickSaved : TokenEditorEvent
}
