package com.easy.wallet.token_manager.chain.manager

import com.easy.wallet.model.ChainInformation

internal sealed interface ChainUiState {
    data object Loading : ChainUiState
    data class Success(val chains: List<ChainInformation>) : ChainUiState
}

internal sealed interface ChainManagerEvent {
    data object ClickAdd : ChainManagerEvent
    data class ClickEdit(val id: Long) : ChainManagerEvent
    data class ClickDeleted(val id: Long) : ChainManagerEvent
}
