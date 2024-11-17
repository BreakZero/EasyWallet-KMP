package com.easy.wallet.send.overview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easy.wallet.android.core.extensions.ObserveAsEvents
import com.easy.wallet.send.SendSharedViewModel
import com.easy.wallet.send.SendUiEvent
import com.easy.wallet.send.SendingBasicUiState

@Composable
internal fun TransactionOverviewRoute(
  viewModel: SendSharedViewModel,
  showErrorMessage: (String) -> Unit,
  navigateTo: (String) -> Unit,
  popBack: () -> Unit
) {
  ObserveAsEvents(flow = viewModel.navigationEvents) {
    when (it) {
      SendUiEvent.NavigateBack -> popBack()
      is SendUiEvent.NavigateTo -> navigateTo(it.destination)
      is SendUiEvent.ShowErrorMessage -> showErrorMessage(it.message)
      else -> Unit
    }
  }
  val basicInfo by viewModel.basicInfoUiState.collectAsStateWithLifecycle()
  val overviewUiState by viewModel.overviewUiState.collectAsStateWithLifecycle()
  EvmChainOverviewScreen(
    assetBalance = (basicInfo as SendingBasicUiState.PrepBasicInfo).assetBalance,
    overviewUiState = overviewUiState,
    onEvent = viewModel::handleEvent
  )
}
