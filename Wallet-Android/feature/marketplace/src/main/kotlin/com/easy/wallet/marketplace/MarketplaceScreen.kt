package com.easy.wallet.marketplace

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.easy.wallet.design.component.DefaultPagingLazyColumn
import com.easy.wallet.marketplace.component.MarketCoinItem
import com.easy.wallet.marketplace.component.TrendingItem
import com.easy.wallet.model.CoinInformation
import com.easy.wallet.model.CoinMarketInformation
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun MarketplaceRoute() {
  val viewModel: MarketplaceViewModel = koinViewModel()
  val trendingCoins by viewModel.trendingCoins.collectAsStateWithLifecycle()
  val topCoinsPaging = viewModel.topCoins.collectAsLazyPagingItems()
  MarketplaceScreen(trendingCoins, topCoinsPaging)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MarketplaceScreen(trendingCoins: List<CoinInformation>, topCoinsPaging: LazyPagingItems<CoinMarketInformation>) {
  Scaffold(
    modifier = Modifier.fillMaxSize(),
    containerColor = Color.Transparent,
    contentColor = MaterialTheme.colorScheme.onBackground,
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = "Powered by CoinGecko",
            style = MaterialTheme.typography.labelSmall
          )
        },
        colors = TopAppBarDefaults
          .topAppBarColors()
          .copy(containerColor = MaterialTheme.colorScheme.inverseOnSurface)
      )
    }
  ) { paddingValues ->
    DefaultPagingLazyColumn(
      modifier = Modifier
        .padding(paddingValues),
      verticalArrangement = Arrangement.spacedBy(12.dp),
      pagingItems = topCoinsPaging,
      headerContainer = {
        if (trendingCoins.isNotEmpty()) {
          item {
            Text(
              modifier = Modifier.padding(horizontal = 16.dp),
              text = "Trending Coins"
            )
          }
          item {
            LazyRow(
              modifier = Modifier.padding(horizontal = 16.dp),
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              items(items = trendingCoins, key = { it.coinId }) {
                TrendingItem(trending = it)
              }
            }
          }
        }
        item {
          Text(
            modifier = Modifier.padding(horizontal = 16.dp),
            text = "Top Coins"
          )
        }
      },
      itemKey = { index -> topCoinsPaging[index]!!.id },
      itemContainer = {
        MarketCoinItem(
          modifier = Modifier.padding(horizontal = 16.dp),
          marketInfo = it
        )
      }
    )
  }
}
