package com.easy.wallet.marketplace.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.easy.wallet.design.component.DynamicAsyncImage
import com.easy.wallet.design.theme.ThemePreviews
import com.easy.wallet.design.ui.EasyWalletTheme
import com.easy.wallet.shared.model.MarketCoin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MarketCoinItem(
    modifier: Modifier = Modifier,
    marketCoin: MarketCoin
) {
    Card(modifier = modifier, onClick = { /*TODO*/ }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            DynamicAsyncImage(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                imageUrl = marketCoin.image, contentDescription = null
            )
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(text = marketCoin.name)
                Text(text = marketCoin.symbol.uppercase())
            }
            Spacer(modifier = Modifier.weight(1.0f))
            Column(horizontalAlignment = Alignment.End) {
                Text(text = marketCoin.currentPrice.toString())
                Text(text = "${marketCoin.priceChangePercentage24h} %")
            }
        }
    }
}

@ThemePreviews
@Composable
private fun MarketCoinItem_Preview() {
    EasyWalletTheme {
        Surface {
            MarketCoinItem(
                marketCoin = MarketCoin(
                    id = "",
                    symbol = "BTC",
                    name = "Bitcoin",
                    image = "https://assets.coingecko.com/coins/images/11795/large/Untitled_design-removebg-preview.png?1696511671",
                    currentPrice = 24567.0,
                    priceChangePercentage24h = 0.005
                )
            )
        }
    }
}
