package com.easy.wallet.news

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.easy.wallet.design.component.PullToRefreshPagingColumn
import com.easy.wallet.model.news.News
import com.easy.wallet.news.component.NewsItemView
import com.easy.wallet.news.navigation.launchCustomChromeTab
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun NewsRoute() {
  val viewModel: NewsViewModel = koinViewModel()
  val newsPagingItems = viewModel.newsUiState.collectAsLazyPagingItems()
  NewsScreen(newsPagingItems = newsPagingItems)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NewsScreen(newsPagingItems: LazyPagingItems<News>) {
  val context = LocalContext.current
  val backgroundColor = MaterialTheme.colorScheme.surface.toArgb()
  Scaffold(
    modifier = Modifier.fillMaxSize(),
    containerColor = Color.Transparent,
    contentColor = MaterialTheme.colorScheme.onBackground,
    topBar = {
      TopAppBar(
        title = {
          Text(
            text = "Powered by BlockChair",
            style = MaterialTheme.typography.labelSmall
          )
        },
        colors = TopAppBarDefaults
          .topAppBarColors()
          .copy(containerColor = MaterialTheme.colorScheme.inverseOnSurface)
      )
    }
  ) { paddingValues ->
    PullToRefreshPagingColumn(
      modifier = Modifier
        .fillMaxSize()
        .padding(paddingValues),
      pagingItems = newsPagingItems,
      verticalArrangement = Arrangement.spacedBy(12.dp),
      itemKey = { index -> newsPagingItems[index]!!.hash },
      itemContainer = { news ->
        NewsItemView(
          modifier = Modifier.padding(horizontal = 16.dp),
          news = news,
          itemClick = {
            launchCustomChromeTab(context, Uri.parse(it.link), backgroundColor)
          }
        )
      }
    )
  }
}
