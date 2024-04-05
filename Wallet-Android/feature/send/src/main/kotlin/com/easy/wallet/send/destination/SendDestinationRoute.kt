package com.easy.wallet.send.destination

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easy.wallet.android.core.extensions.ObserveAsEvents
import com.easy.wallet.send.SendSharedViewModel
import com.easy.wallet.send.SendUiEvent

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun SendDestinationRoute(
    viewModel: SendSharedViewModel,
    onNext: () -> Unit
) {

    ObserveAsEvents(flow = viewModel.navigationEvents) {
        when(it) {
            SendUiEvent.ClickNext -> onNext()
            else -> Unit
        }
    }

    val destinationState = viewModel.destination
    val uiState by viewModel.sendUiState.collectAsStateWithLifecycle()
    EvmChainDestinationScreen(uiState = uiState, textFieldState = destinationState, onEvent = viewModel::handleEvent)
}
