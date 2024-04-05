package com.easy.wallet.send.amount

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easy.wallet.android.core.extensions.ObserveAsEvents
import com.easy.wallet.send.SendSharedViewModel
import com.easy.wallet.send.SendUiEvent
import com.easy.wallet.send.SendUiState

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun SendAmountRoute(
    viewModel: SendSharedViewModel,
    onNext: () -> Unit
) {
    ObserveAsEvents(flow = viewModel.navigationEvents) {
        when (it) {
            SendUiEvent.ClickNext -> onNext()
            else -> Unit
        }
    }
    val uiState by viewModel.sendUiState.collectAsStateWithLifecycle()

    EvmChainSendAmountScreen(uiState = uiState as SendUiState.Success, onEvent = viewModel::handleEvent)
}
