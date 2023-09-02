package com.easy.wallet.marketplace

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun MarketplaceRoute() {
    val viewModel: MarketplaceViewModel = koinViewModel()
    MarketplaceScreen()
}

@Composable
internal fun MarketplaceScreen(
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Marketplace")
    }
}