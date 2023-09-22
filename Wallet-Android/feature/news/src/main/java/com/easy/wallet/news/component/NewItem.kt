package com.easy.wallet.news.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.easy.wallet.model.news.News

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun NewItemView(
    modifier: Modifier = Modifier,
    news: News,
    itemClick: (News) -> Unit
) {
    Card(
        modifier = modifier,
        onClick = {
            itemClick(news)
        },
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 16.dp),
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = news.title,
                style = MaterialTheme.typography.titleMedium,
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = news.description,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.bodyLarge,
            )
            Text(
                text = news.source,
                modifier = Modifier.fillMaxWidth(),
                style = MaterialTheme.typography.bodySmall,
            )
        }
    }
}