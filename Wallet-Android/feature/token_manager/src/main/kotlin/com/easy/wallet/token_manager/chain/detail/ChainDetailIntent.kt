package com.easy.wallet.token_manager.chain.detail

import com.easy.wallet.model.ChainInformation

internal sealed interface ChainDetailUiState {
    data object Loading: ChainDetailUiState
    data class Error(val error: String): ChainDetailUiState
    data class Success(val chainInformation: ChainInformation): ChainDetailUiState
}
