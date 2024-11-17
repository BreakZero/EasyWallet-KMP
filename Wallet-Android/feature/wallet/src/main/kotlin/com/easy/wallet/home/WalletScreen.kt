package com.easy.wallet.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easy.wallet.android.core.extensions.ObserveAsEvents
import com.easy.wallet.design.component.LoadingWheel
import com.easy.wallet.home.component.GuestContent
import com.easy.wallet.home.component.UserHomeContent
import com.easy.wallet.model.asset.CoinModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun WalletRoute(
  viewModel: WalletViewModel = koinViewModel(),
  onCreateWallet: () -> Unit,
  onRestoreWallet: () -> Unit,
  onCoinItemClick: (CoinModel) -> Unit,
  navigateToSettings: () -> Unit
) {
  val uiState by viewModel.homeUiState.collectAsStateWithLifecycle()
  val isRefreshing by viewModel.isRefreshing.collectAsStateWithLifecycle()

  ObserveAsEvents(flow = viewModel.navigationEvents) { event ->
    when (event) {
      WalletEvent.CreateWallet -> onCreateWallet()
      WalletEvent.RestoreWallet -> onRestoreWallet()
      WalletEvent.SettingsClicked -> navigateToSettings()
      is WalletEvent.OnCoinClicked -> onCoinItemClick(event.coin)
      else -> Unit
    }
  }

  WalletScreen(
    walletUiState = uiState,
    isRefreshing = isRefreshing,
    onEvent = viewModel::handleEvent
  )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun WalletScreen(
  isRefreshing: Boolean,
  walletUiState: WalletUiState,
  onEvent: (WalletEvent) -> Unit
) {
  Scaffold(
    modifier = Modifier.fillMaxSize(),
    containerColor = Color.Transparent,
    contentColor = MaterialTheme.colorScheme.onBackground,
    topBar = {
      TopAppBar(
        title = { /*TODO*/ },
        navigationIcon = {
          if (walletUiState is WalletUiState.WalletUiState) {
            IconButton(onClick = { onEvent(WalletEvent.SettingsClicked) }) {
              Icon(imageVector = Icons.Default.Settings, contentDescription = null)
            }
          }
        },
        colors = TopAppBarDefaults
          .topAppBarColors()
          .copy(containerColor = MaterialTheme.colorScheme.inverseOnSurface)
      )
    }
  ) { paddingValues ->
    when (walletUiState) {
      is WalletUiState.Initial -> {
        Box(
          modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
          contentAlignment = Alignment.Center
        ) {
          LoadingWheel(contentDesc = "")
        }
      }
      is WalletUiState.WalletUiState -> {
        UserHomeContent(
          modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues),
          isRefreshing = isRefreshing,
          walletUiState = walletUiState,
          onEvent = onEvent
        )
      }
      is WalletUiState.GuestUserUiState -> {
        GuestContent(
          modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 16.dp)
            .padding(bottom = 16.dp),
          onEvent = onEvent
        )
      }
    }
  }
}
