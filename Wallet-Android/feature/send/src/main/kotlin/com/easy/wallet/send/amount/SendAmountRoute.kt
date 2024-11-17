package com.easy.wallet.send.amount

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easy.wallet.android.core.extensions.ObserveAsEvents
import com.easy.wallet.send.SendSharedViewModel
import com.easy.wallet.send.SendUiEvent
import com.easy.wallet.send.SendingBasicUiState

@Composable
internal fun SendAmountRoute(
  viewModel: SendSharedViewModel,
  navigateTo: (String) -> Unit,
  popBack: () -> Unit
) {
  ObserveAsEvents(flow = viewModel.navigationEvents) {
    when (it) {
      is SendUiEvent.NavigateTo -> navigateTo(it.destination)
      SendUiEvent.NavigateBack -> popBack()
      else -> Unit
    }
  }
  val uiState by viewModel.basicInfoUiState.collectAsStateWithLifecycle()
  val amountUiState by viewModel.amountUiState.collectAsStateWithLifecycle()

  EvmChainSendAmountScreen(
    assetBalance = (uiState as SendingBasicUiState.PrepBasicInfo).assetBalance,
    amountUiState = amountUiState,
    onEvent = viewModel::handleEvent
  )
}
