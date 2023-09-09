package com.easy.wallet.home

import com.easy.wallet.home.component.ActionSheetMenu
import com.easy.wallet.model.token.Token

internal data class GuestUiState(
    val isActionSheetOpen: Boolean = false,
    val actions: List<ActionSheetMenu> = emptyList()
)

internal data class WalletUiState(
    val tokens: List<Token> = emptyList()
)
