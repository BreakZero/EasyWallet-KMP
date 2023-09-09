package com.easy.wallet.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easy.wallet.core.result.Result
import com.easy.wallet.design.component.LoadingWheel
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
    hasSetup: Result<Boolean>,
    onEvent: (HomeEvent) -> Unit
) {
    when (hasSetup) {
        is Result.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                LoadingWheel(contentDesc = "")
            }
        }

        is Result.Success -> {
            if (hasSetup.data) {
                UserHomeContent(modifier = modifier, walletUiState = walletUiState)
            } else {
                GuestContent(modifier = modifier, guestUiState = guestUiState, onEvent = onEvent)
            }
        }

        else -> Unit
    }

}