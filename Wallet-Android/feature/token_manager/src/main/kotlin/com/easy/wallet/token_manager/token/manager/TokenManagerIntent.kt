package com.easy.wallet.token_manager.token.manager

sealed interface TokenManagerUiState {
    data object Loading : TokenManagerUiState
    data class Success(
        val tokens: List<String>
    ) : TokenManagerUiState
}

sealed interface TokenManagerEvent {

    data object ClickAdd : TokenManagerEvent
    data class ClickDelete(val id: Long) : TokenManagerEvent

    data class ClickEdit(val id: Long) : TokenManagerEvent
}
