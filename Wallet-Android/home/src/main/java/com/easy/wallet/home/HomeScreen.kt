package com.easy.wallet.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easy.wallet.home.component.GuestContent
import com.easy.wallet.home.component.UserHomeContent
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun HomeRoute(
    viewModel: HomeViewModel = koinViewModel(),
    onCreateWallet: () -> Unit,
    onRestoreWallet: () -> Unit
) {
    val guestUiState by viewModel.guestUiState.collectAsStateWithLifecycle()
    val walletUiState by viewModel.walletUiState.collectAsStateWithLifecycle()
    val hasSetup by viewModel.hasSetup.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = viewModel) {
        viewModel.uiEvent.collect { uiEvent ->
            when (uiEvent) {
                HomeUiEvent.OnCreateWallet -> onCreateWallet()
                HomeUiEvent.OnRestoreWallet -> onRestoreWallet()
            }
        }
    }
    HomeScreen(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp),
        guestUiState = guestUiState,
        walletUiState = walletUiState,
        hasSetup = hasSetup,
        onEvent = viewModel::handleUiEvent
    )
}

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    guestUiState: GuestUiState,
    walletUiState: WalletUiState,
    hasSetup: Boolean,
    onEvent: (HomeEvent) -> Unit
) {
    if (hasSetup) {
        UserHomeContent(modifier = modifier, walletUiState = walletUiState)
    } else {
        GuestContent(modifier = modifier, guestUiState = guestUiState, onEvent = onEvent)
    }
}