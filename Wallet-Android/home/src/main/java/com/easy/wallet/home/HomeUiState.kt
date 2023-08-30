package com.easy.wallet.home

import com.easy.wallet.home.component.ActionSheetMenu

internal data class GuestUiState(
    val isActionSheetOpen: Boolean = false,
    val actions: List<ActionSheetMenu> = emptyList()
)

internal data class WalletUiState(
    val address: String
)
