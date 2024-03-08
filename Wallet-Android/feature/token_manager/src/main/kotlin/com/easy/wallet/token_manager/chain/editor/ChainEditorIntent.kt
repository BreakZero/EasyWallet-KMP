package com.easy.wallet.token_manager.chain.editor

internal data class ChainEditorUiState(
    val name: String,
    val website: String,
    val explorer: String,
    val rpcUrl: String,
    val chainId: String
)

internal sealed interface ChainEditorUiEvent {
    data object OnSavedClick: ChainEditorUiEvent
}
