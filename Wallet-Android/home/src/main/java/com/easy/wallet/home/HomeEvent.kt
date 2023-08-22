package com.easy.wallet.home

import com.easy.wallet.home.component.ActionSheetMenu

internal sealed interface HomeEvent {
    data object CreateWalletEvent: HomeEvent
    data object ImportWalletEvent: HomeEvent
    data object CloseActionSheet: HomeEvent
}

internal sealed interface HomeUiEvent {

}