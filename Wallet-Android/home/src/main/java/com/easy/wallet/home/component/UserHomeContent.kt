package com.easy.wallet.home.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.easy.wallet.home.WalletUiState

@Composable
internal fun UserHomeContent(
    modifier: Modifier = Modifier,
    walletUiState: WalletUiState
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Text("ETH Address: ${walletUiState.address}")
    }
}