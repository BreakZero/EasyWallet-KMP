package com.easy.wallet.assetmanager.platform.manager

import com.easy.wallet.model.asset.AssetPlatform

internal sealed interface ChainUiState {
  data object Loading : ChainUiState

  data class Success(
    val platforms: List<AssetPlatform>
  ) : ChainUiState
}

internal sealed interface ChainManagerEvent {
  data object ClickAdd : ChainManagerEvent

  data class ClickEdit(
    val id: String
  ) : ChainManagerEvent

  data class ClickDeleted(
    val id: String
  ) : ChainManagerEvent

  data object InitialDefaultData : ChainManagerEvent
}
