package com.easy.wallet.home

import com.easy.wallet.home.component.ActionSheetMenu

internal data class HomeUiState(
    val hasSetup: Boolean = false,
    val isActionSheetOpen: Boolean = false,
    val actions: List<ActionSheetMenu> = emptyList()
)
