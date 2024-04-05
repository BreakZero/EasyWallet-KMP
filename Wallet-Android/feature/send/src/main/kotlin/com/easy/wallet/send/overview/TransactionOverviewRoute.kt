package com.easy.wallet.send.overview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easy.wallet.android.core.extensions.ObserveAsEvents
import com.easy.wallet.send.SendSharedViewModel
import com.easy.wallet.send.SendUiState


@Composable
internal fun TransactionOverviewRoute(
    viewModel: SendSharedViewModel
) {
    ObserveAsEvents(flow = viewModel.navigationEvents) {

    }
    val uiState by viewModel.sendUiState.collectAsStateWithLifecycle()
    EvmChainOverviewScreen(uiState = uiState as SendUiState.Success)
}

