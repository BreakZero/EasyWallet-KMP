package com.easy.wallet.token_manager.token.manager

import com.easy.wallet.model.TokenInformation

sealed interface TokenManagerUiState {
    data object Loading : TokenManagerUiState
    data class Success(
        val tokens: List<TokenInformation>
    ) : TokenManagerUiState
}

sealed interface TokenManagerEvent {
    data object ClickPopBack : TokenManagerEvent
    data object ClickAdd : TokenManagerEvent
    data class ClickDelete(val ids: List<String>) : TokenManagerEvent

    data object ClickInEditModel : TokenManagerEvent
    data class ClickEdit(val id: String) : TokenManagerEvent
}
