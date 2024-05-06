package com.easy.wallet.assetmanager.platform.detail

import com.easy.wallet.model.asset.AssetPlatform

internal sealed interface ChainDetailUiState {
    data object Loading: ChainDetailUiState
    data class Error(val error: String): ChainDetailUiState
    data class Success(val assetPlatform: AssetPlatform): ChainDetailUiState
}
