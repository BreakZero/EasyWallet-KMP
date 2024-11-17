package com.easy.wallet.news.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.easy.wallet.model.news.News

@Composable
internal fun NewsItemView(
  modifier: Modifier = Modifier,
  news: News,
  itemClick: (News) -> Unit
) {
  val tags = remember {
    news.tags.split(",").take(3)
  }
  Card(
    modifier = modifier,
    onClick = {
      itemClick(news)
    }
  ) {
    Column(
      modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 12.dp, vertical = 16.dp)
    ) {
      Text(
        modifier = Modifier.fillMaxWidth(),
        text = news.title,
        style = MaterialTheme.typography.titleMedium
      )
      Text(
        modifier = Modifier.fillMaxWidth(),
        text = news.description,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.bodyLarge
      )
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
      ) {
        Text(
          text = news.source,
          style = MaterialTheme.typography.bodySmall
        )
        LazyRow(
          modifier = Modifier.weight(1F),
          horizontalArrangement = Arrangement.spacedBy(4.dp, alignment = Alignment.End)
        ) {
          items(tags) {
            Text(
              text = it,
              color = MaterialTheme.colorScheme.tertiaryContainer,
              modifier = Modifier
                .background(
                  MaterialTheme.colorScheme.onTertiaryContainer,
                  shape = RoundedCornerShape(4.dp)
                ).padding(horizontal = 4.dp, vertical = 2.dp),
              style = MaterialTheme.typography.bodySmall
            )
          }
        }
      }
    }
  }
}
