package com.easy.wallet.assetmanager.coin.manager

import com.easy.wallet.model.asset.AssetPlatform
import com.easy.wallet.model.asset.BasicCoin

sealed interface TokenManagerUiState {
  data object Loading : TokenManagerUiState

  data class Success(
    val groupOfCoin: Map<AssetPlatform, List<BasicCoin>>
  ) : TokenManagerUiState
}

sealed interface TokenManagerEvent {
  data object ClickPopBack : TokenManagerEvent

  data object ClickAdd : TokenManagerEvent

  data class ClickDelete(
    val ids: List<String>
  ) : TokenManagerEvent

  data object ClickInEditModel : TokenManagerEvent

  data class ClickEdit(
    val id: String
  ) : TokenManagerEvent

  data class OnActivatedChanged(
    val isChecked: Boolean
  ) : TokenManagerEvent
}
