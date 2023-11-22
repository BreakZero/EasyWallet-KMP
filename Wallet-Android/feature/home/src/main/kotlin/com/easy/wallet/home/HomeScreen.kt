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
import com.easy.wallet.design.component.LoadingWheel
import com.easy.wallet.home.component.GuestContent
import com.easy.wallet.home.component.UserHomeContent
import com.easy.wallet.model.token.Token
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun HomeRoute(
    viewModel: HomeViewModel = koinViewModel(),
    onCreateWallet: () -> Unit,
    onRestoreWallet: () -> Unit,
    onTokenClick: (Token) -> Unit,
    navigateToSettings: () -> Unit
) {
    val uiState by viewModel.homeUiState.collectAsStateWithLifecycle()
    LaunchedEffect(key1 = viewModel) {
        viewModel.eventFlow.collect { event ->
            when (event) {
                HomeEvent.OnCreateWallet -> onCreateWallet()
                HomeEvent.OnRestoreWallet -> onRestoreWallet()
                HomeEvent.ClickSettings -> navigateToSettings()
                is HomeEvent.ClickToken -> onTokenClick(event.token)
                else -> Unit
            }
        }
    }
    HomeScreen(
        modifier = Modifier
            .fillMaxSize(),
        homeUiState = uiState,
        onEvent = viewModel::handleEvent,
    )
}

@Composable
internal fun HomeScreen(
    modifier: Modifier = Modifier,
    homeUiState: HomeUiState,
    onEvent: (HomeEvent) -> Unit
) {
    when (homeUiState) {
        is HomeUiState.Fetching -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                LoadingWheel(contentDesc = "")
            }
        }

        is HomeUiState.WalletUiState -> {
            UserHomeContent(
                modifier = modifier,
                uiState = homeUiState,
                onEvent = onEvent,
            )
        }
        is HomeUiState.GuestUiState -> {
            GuestContent(modifier = modifier, uiState = homeUiState, onEvent = onEvent)
        }
    }
}
