package com.easy.wallet.marketplace.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.easy.wallet.design.component.DataPoint
import com.easy.wallet.design.component.DynamicAsyncImage
import com.easy.wallet.design.component.LineChart
import com.easy.wallet.design.theme.ThemePreviews
import com.easy.wallet.design.ui.EasyWalletTheme
import com.easy.wallet.shared.model.MarketCoin
import kotlinx.collections.immutable.toImmutableList


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
                .height(IntrinsicSize.Min)
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
            val graphColor = if (marketCoin.priceChangePercentage24h > 0.0) {
                MaterialTheme.colorScheme.error
            } else MaterialTheme.colorScheme.tertiary
            LineChart(
                modifier = Modifier
                    .height(32.dp)
                    .aspectRatio(2.0f),
                data = marketCoin.price.map { DataPoint(it) }.toImmutableList(),
                graphColor = graphColor,
                showDashedLine = false
            )
            Spacer(modifier = Modifier.width(8.dp))
            Column(horizontalAlignment = Alignment.End) {
                Text(text = marketCoin.currentPrice.toString())
                Text(text = "${marketCoin.priceChangePercentage24h} %", color = graphColor)
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
                    priceChangePercentage24h = 0.005,
                    price = listOf(
                        276.2353290431993,
                        2180.718988609708,
                        2963.3886532204415,
                        235.8069782438333,
                        235.113656215793,
                        2368.0627005334773,
                        2303.210836144689,
                        227.0630351332684,
                        2216.166243224054
                    )
                )
            )
        }
    }
}
