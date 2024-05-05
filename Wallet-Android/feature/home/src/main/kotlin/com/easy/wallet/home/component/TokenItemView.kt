package com.easy.wallet.home.component

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.easy.wallet.design.component.DynamicAsyncImage
import com.easy.wallet.home.HomeEvent
import com.easy.wallet.model.asset.AssetBalance

@Composable
internal fun TokenItemView(
    modifier: Modifier = Modifier,
    assetBalance: AssetBalance,
    onEvent: (HomeEvent) -> Unit
) {
    Card(modifier = modifier, onClick = { onEvent(HomeEvent.OnCoinClicked(assetBalance)) }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    horizontal = 16.dp,
                    vertical = 12.dp,
                ),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            DynamicAsyncImage(
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape),
                imageUrl = assetBalance.logoURI,
                contentDescription = assetBalance.name,
            )
            Column(
                modifier = Modifier.fillMaxHeight(),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier.padding(start = 12.dp),
                    text = assetBalance.name
                )
                Text(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .border(
                            1.dp,
                            color = MaterialTheme.colorScheme.tertiary,
                            shape = RoundedCornerShape(4.dp)
                        ),
                    style = MaterialTheme.typography.labelSmall,
                    text = assetBalance.platform.shortName
                )
            }

            Spacer(modifier = Modifier.weight(1.0f))
            Text(text = "${assetBalance.balance} ${assetBalance.symbol}")
        }
    }
}
