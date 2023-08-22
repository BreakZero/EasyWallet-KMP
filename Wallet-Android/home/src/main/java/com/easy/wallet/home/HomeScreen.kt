package com.easy.wallet.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easy.wallet.home.component.GuestContent
import com.easy.wallet.home.component.UserHomeContent
import com.easy.wallet.home.component.WalletActionSheet
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun HomeRoute(
    viewModel: HomeViewModel = koinViewModel()
) {
    val homeUiState by viewModel.homeUiState.collectAsStateWithLifecycle()
    HomeScreen(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        uiState = homeUiState,
        onEvent = viewModel::handleUiEvent
    )
}

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    uiState: HomeUiState,
    onEvent: (HomeEvent) -> Unit
) {
    if (uiState.isUserExists) {
        UserHomeContent(modifier = modifier)
    } else {
        GuestContent(modifier = modifier, onEvent = onEvent)
    }
    if (uiState.isActionSheetOpen) {
        WalletActionSheet(menus = uiState.actions, onEvent = onEvent)
    }
}