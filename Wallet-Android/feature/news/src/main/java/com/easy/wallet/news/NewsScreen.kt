package com.easy.wallet.news

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.easy.wallet.design.component.DefaultPagingStateColumn
import com.easy.wallet.model.news.News
import com.easy.wallet.news.component.NewsItemView
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun NewsRoute(
    navigateToDetail: (String) -> Unit
) {
    val viewModel: NewsViewModel = koinViewModel()
    val news = viewModel.newsUiState.collectAsLazyPagingItems()
    LaunchedEffect(key1 = viewModel) {
        viewModel.eventFlow.collect {
            when (it) {
                is NewsEvent.ClickItem -> navigateToDetail(it.url)
                else -> Unit
            }
        }
    }
    NewsScreen(
        newsPaging = news,
        onEvent = viewModel::handleEvent,
    )
}

@Composable
internal fun NewsScreen(
    newsPaging: LazyPagingItems<News>,
    onEvent: (NewsEvent) -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {
        DefaultPagingStateColumn(
            paging = newsPaging,
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            itemView = { news ->
                NewsItemView(news = news, itemClick = {
                    onEvent(NewsEvent.ClickItem(it.link))
                })
            },
        )
    }
}
