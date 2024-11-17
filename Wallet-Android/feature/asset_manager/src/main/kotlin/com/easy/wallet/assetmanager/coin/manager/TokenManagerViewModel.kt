package com.easy.wallet.assetmanager.coin.manager

import androidx.lifecycle.viewModelScope
import com.easy.wallet.android.core.BaseViewModel
import com.easy.wallet.shared.data.repository.asset.CoinRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

internal class TokenManagerViewModel(
  localAssetRepository: CoinRepository
) : BaseViewModel<TokenManagerEvent>() {
  val tokenManagerUiState = localAssetRepository
    .findAllCoinStream()
    .map {
      TokenManagerUiState.Success(groupOfCoin = it.groupBy { it.platform })
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(3_000), TokenManagerUiState.Loading)

  override fun handleEvent(event: TokenManagerEvent) {
    when (event) {
      is TokenManagerEvent.ClickDelete -> {
        onDeleted(event.ids)
      }

      is TokenManagerEvent.ClickInEditModel -> {}
      TokenManagerEvent.ClickAdd, is TokenManagerEvent.ClickEdit, TokenManagerEvent.ClickPopBack -> {
        dispatchEvent(event)
      }

      is TokenManagerEvent.OnActivatedChanged -> Unit
    }
  }

  private fun onDeleted(ids: List<String>) {}
}
