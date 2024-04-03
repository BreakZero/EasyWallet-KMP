package com.easy.wallet.send.amount

import androidx.compose.runtime.Composable
import com.easy.wallet.android.core.extensions.ObserveAsEvents
import com.easy.wallet.send.SendSharedViewModel
import com.easy.wallet.send.SendUiEvent

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
    val amount = viewModel.amount
    EvmChainSendAmountScreen(amount = amount, onEvent = viewModel::handleEvent)
}
