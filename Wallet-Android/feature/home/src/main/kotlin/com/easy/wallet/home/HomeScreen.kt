package com.easy.wallet.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easy.wallet.android.core.extensions.ObserveAsEvents
import com.easy.wallet.design.component.LoadingWheel
import com.easy.wallet.home.component.GuestContent
import com.easy.wallet.home.component.UserHomeContent
import com.easy.wallet.model.token.Token
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.withLock
import org.koin.androidx.compose.koinViewModel
import kotlin.coroutines.resume

@Composable
internal fun HomeRoute(
    viewModel: HomeViewModel = koinViewModel(),
    onCreateWallet: () -> Unit,
    onRestoreWallet: () -> Unit,
    onTokenClick: (Token) -> Unit,
    navigateToSettings: () -> Unit
) {
    val uiState by viewModel.homeUiState.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        // Create an observer that triggers our remembered callbacks
        // for sending analytics events
        val observer = LifecycleEventObserver { _, event ->
            println("===== ${event.targetState.name}")
        }

        // Add the observer to the lifecycle
        lifecycleOwner.lifecycle.addObserver(observer)

        // When the effect leaves the Composition, remove the observer
        onDispose {
            lifecycleOwner.lifecycle.removeObserver(observer)
        }
    }

    ObserveAsEvents(flow = viewModel.navigationEvents) { event ->
        when (event) {
            HomeEvent.CreateWallet -> onCreateWallet()
            HomeEvent.RestoreWallet -> onRestoreWallet()
            HomeEvent.SettingsClicked -> navigateToSettings()
            is HomeEvent.TokenClicked -> onTokenClick(event.token)
            else -> Unit
        }
    }

    HomeScreen(
        homeUiState = uiState,
        onEvent = viewModel::handleEvent,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun HomeScreen(
    homeUiState: HomeUiState,
    onEvent: (HomeEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = { /*TODO*/ },
                navigationIcon = {
                    if (homeUiState is HomeUiState.WalletUiState) {
                        IconButton(onClick = { onEvent(HomeEvent.SettingsClicked) }) {
                            Icon(imageVector = Icons.Default.Settings, contentDescription = null)
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        when (homeUiState) {
            is HomeUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    LoadingWheel(contentDesc = "")
                }
            }

            is HomeUiState.WalletUiState -> {
                UserHomeContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues),
                    walletUiState = homeUiState,
                    onEvent = onEvent,
                )
            }

            is HomeUiState.GuestUiState -> {
                GuestContent(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 16.dp),
                    guestUiState = homeUiState,
                    onEvent = onEvent
                )
            }
        }
    }
}
