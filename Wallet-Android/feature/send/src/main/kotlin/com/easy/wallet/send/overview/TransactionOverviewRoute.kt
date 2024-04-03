package com.easy.wallet.send.overview

import androidx.compose.runtime.Composable
import com.easy.wallet.android.core.extensions.ObserveAsEvents
import com.easy.wallet.send.SendSharedViewModel


@Composable
internal fun TransactionOverviewRoute(
    viewModel: SendSharedViewModel
) {
    ObserveAsEvents(flow = viewModel.navigationEvents) {

    }
    EvmChainOverviewScreen()
}

