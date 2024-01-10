package com.easy.wallet.marketplace

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easy.wallet.design.component.LoadingWheel
import com.easy.wallet.marketplace.component.MarketCoinItem
import com.easy.wallet.marketplace.component.TrendingItem
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun MarketplaceRoute() {
    val viewModel: MarketplaceViewModel = koinViewModel()
    val marketplaceUiState by viewModel.marketplaceUiState.collectAsStateWithLifecycle()
    MarketplaceScreen(marketplaceUiState)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MarketplaceScreen(marketplaceUiState: MarketplaceUiState) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Powered by CoinGecko",
                    style = MaterialTheme.typography.labelSmall
                )
            })
        }
    ) { paddingValues ->
        when (marketplaceUiState) {
            is MarketplaceUiState.Loading -> {
                Box(
                    modifier = Modifier.fillMaxSize().padding(paddingValues),
                    contentAlignment = Alignment.Center
                ) {
                    LoadingWheel(contentDesc = "")
                }
            }

            is MarketplaceUiState.Success -> {
                LazyColumn(
                    modifier = Modifier
                        .padding(paddingValues)
                        .padding(bottom = 8.dp),
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                        ) {
                            items(marketplaceUiState.trends) {
                                TrendingItem(trending = it)
                            }
                        }
                    }
                    items(marketplaceUiState.topCoins) {
                        MarketCoinItem(marketCoin = it)
                    }
                }
            }

            else -> Unit
        }

    }
}
