package com.easy.wallet.marketplace.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.easy.wallet.design.component.DynamicAsyncImage
import com.easy.wallet.design.theme.ThemePreviews
import com.easy.wallet.design.ui.EasyWalletTheme
import com.easy.wallet.model.CoinInformation

@Composable
internal fun TrendingItem(
    modifier: Modifier = Modifier,
    trending: CoinInformation
) {
    Card(modifier = modifier, onClick = { /*TODO*/ }) {
        Column(
            modifier = Modifier
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            DynamicAsyncImage(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                imageUrl = trending.largeImage,
                contentDescription = trending.id
            )
            Text(
                text = "${trending.name}(${trending.symbol.uppercase()})",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}


@ThemePreviews
@Composable
private fun TrendingItem_Preview() {
    EasyWalletTheme {
        Surface {
            TrendingItem(
                trending = CoinInformation(
                    id = "",
                    coinId = 1,
                    name = "Bitcoin",
                    symbol = "BTC",
                    thumb = "https://assets.coingecko.com/coins/images/325/large/Tether.png?1696501661",
                    largeImage = "https://assets.coingecko.com/coins/images/11795/large/Untitled_design-removebg-preview.png?1696511671"
                )
            )
        }
    }
}
