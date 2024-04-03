package com.easy.wallet.send.destination

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.runtime.Composable
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
    EvmChainDestinationScreen(textFieldState = destinationState, onEvent = viewModel::handleEvent)
}
