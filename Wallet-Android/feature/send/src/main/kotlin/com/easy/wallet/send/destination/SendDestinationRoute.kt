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

    val prepUiState by viewModel.basicInfoState.collectAsStateWithLifecycle()
    val destinationUiState by viewModel.destinationUiState.collectAsStateWithLifecycle()
    EvmChainDestinationScreen(
        uiState = prepUiState,
        textFieldState = viewModel.destination,
        destinationUiState = destinationUiState,
        onEvent = viewModel::handleEvent
    )
}
