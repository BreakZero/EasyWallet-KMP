package com.easy.wallet.send.overview

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easy.wallet.android.core.extensions.ObserveAsEvents
import com.easy.wallet.send.SendSharedViewModel
import com.easy.wallet.send.SendingBasicUiState


@Composable
internal fun TransactionOverviewRoute(
    viewModel: SendSharedViewModel
) {
    ObserveAsEvents(flow = viewModel.navigationEvents) {

    }
    val basicInfo by viewModel.basicInfoUiState.collectAsStateWithLifecycle()
    val overviewUiState by viewModel.overviewUiState.collectAsStateWithLifecycle()
    EvmChainOverviewScreen(
        basicInfo = basicInfo as SendingBasicUiState.PrepBasicInfo,
        overviewUiState = overviewUiState,
        onEvent = viewModel::handleEvent
    )
}

