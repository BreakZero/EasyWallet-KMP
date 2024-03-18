package com.easy.wallet.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.easy.wallet.design.component.DynamicAsyncImage
import com.easy.wallet.home.HomeEvent
import com.easy.wallet.shared.model.ExtraToken

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TokenItemView(
    modifier: Modifier = Modifier,
    extraToken: ExtraToken,
    onEvent: (HomeEvent) -> Unit
) {
    val token = extraToken.token
    val balance = extraToken.balance
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                onEvent(HomeEvent.TokenClicked(token))
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
            imageUrl = token.iconUri,
            contentDescription = token.name,
        )
        Text(modifier = Modifier.padding(start = 12.dp), text = token.name)
        Spacer(modifier = Modifier.weight(1.0f))
        Text(text = "${balance.approximate()} ${token.symbol}")
    }
}
