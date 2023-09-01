package com.easy.wallet.marketplace

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easy.wallet.data.nft.model.OpenSeaNftDto
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun MarketplaceRoute() {
    val viewModel: MarketplaceViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    MarketplaceScreen(uiState = uiState)
}

@Composable
internal fun MarketplaceScreen(
    uiState: List<OpenSeaNftDto>
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Marketplace")
    }
}