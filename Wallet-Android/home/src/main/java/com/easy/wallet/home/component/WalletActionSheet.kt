package com.easy.wallet.home.component

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.easy.wallet.home.HomeEvent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WalletActionSheet(
    modifier: Modifier = Modifier,
    menus: List<ActionSheetMenu>,
    onEvent: (HomeEvent) -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    ModalBottomSheet(
        modifier = modifier,
        sheetState = bottomSheetState,
        onDismissRequest = {
            onEvent(HomeEvent.CloseActionSheet)
        }
    ) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(menus, key = { it.title }) { menu ->
                ActionSheetItem(menu = menu) {
                    when(menu) {
                        ActionSheetMenu.CREATE_BY_SEED -> {}
                        else -> Unit
                    }
                }
            }
        }
    }
}