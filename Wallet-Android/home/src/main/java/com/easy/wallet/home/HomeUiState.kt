package com.easy.wallet.home

import com.easy.wallet.data.model.ExtraToken
import com.easy.wallet.home.component.ActionSheetMenu

internal sealed interface HomeUiState {
    data object Fetching : HomeUiState
    data class GuestUiState(
        val isActionSheetOpen: Boolean = false,
        val actions: List<ActionSheetMenu> = emptyList()
    ) : HomeUiState

    data class WalletUiState(
        val tokens: List<ExtraToken>
    ) : HomeUiState
}
