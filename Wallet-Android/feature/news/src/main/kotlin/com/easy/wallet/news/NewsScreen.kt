package com.easy.wallet.news

import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.easy.wallet.android.core.extensions.ObserveAsEvents
import com.easy.wallet.design.component.DefaultPagingStateColumn
import com.easy.wallet.model.news.News
import com.easy.wallet.news.component.NewsItemView
import com.easy.wallet.news.navigation.launchCustomChromeTab
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun NewsRoute() {
    val viewModel: NewsViewModel = koinViewModel()
    val news = viewModel.newsUiState.collectAsLazyPagingItems()
    NewsScreen(
        newsPaging = news
    )
}

@Composable
internal fun NewsScreen(
    newsPaging: LazyPagingItems<News>
) {
    val context = LocalContext.current
    val backgroundColor = MaterialTheme.colorScheme.surface.toArgb()
    Box(modifier = Modifier.fillMaxSize()) {
        DefaultPagingStateColumn<News>(
            paging = newsPaging,
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            itemView = { news ->
                NewsItemView(news = news, itemClick = {
                    launchCustomChromeTab(context, Uri.parse(it.link), backgroundColor)
                })
            },
        )
    }
}
