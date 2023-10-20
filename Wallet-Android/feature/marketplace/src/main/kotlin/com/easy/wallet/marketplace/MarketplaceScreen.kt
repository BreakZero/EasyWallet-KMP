package com.easy.wallet.marketplace

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun MarketplaceRoute() {
    val viewModel: MarketplaceViewModel = koinViewModel()
    val content by viewModel.content.collectAsStateWithLifecycle()
    MarketplaceScreen(content)
}

@Composable
internal fun MarketplaceScreen(content: String) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Text(text = content)
    }
}
