package com.easy.wallet.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.easy.wallet.design.component.DynamicAsyncImage
import com.easy.wallet.home.WalletEvent
import com.easy.wallet.model.asset.AssetBalance

@Composable
internal fun CoinItemView(
    modifier: Modifier = Modifier,
    assetBalance: AssetBalance,
    onEvent: (WalletEvent) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onEvent(WalletEvent.OnCoinClicked(assetBalance))
            }
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
        Text(modifier = Modifier.padding(start = 12.dp), text = assetBalance.name)
        Spacer(modifier = Modifier.weight(1.0f))
        Text(text = "${assetBalance.balance} ${assetBalance.symbol}")
    }
}
