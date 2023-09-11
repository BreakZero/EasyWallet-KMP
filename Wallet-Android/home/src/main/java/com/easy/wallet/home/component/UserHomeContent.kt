package com.easy.wallet.home.component

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.easy.wallet.design.component.ThemePreviews
import com.easy.wallet.design.ui.EWalletTheme
import com.easy.wallet.home.HomeEvent
import com.easy.wallet.home.WalletUiState

@Composable
internal fun UserHomeContent(
    modifier: Modifier = Modifier,
    walletUiState: WalletUiState,
    onEvent: (HomeEvent) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(IntrinsicSize.Min),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(
                modifier = Modifier.padding(vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = {
                    onEvent(HomeEvent.ClickSettings)
                }) {
                    Icon(
                        modifier = Modifier
                            .size(48.dp)
                            .padding(4.dp),
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = ""
                    )
                }
                Text(text = "Account Name", style = MaterialTheme.typography.titleSmall)
            }
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(4.dp))
                    .clickable { },
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Ethereum Network", style = MaterialTheme.typography.bodySmall)
                Icon(imageVector = Icons.Default.KeyboardArrowDown, contentDescription = "")
            }
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp)
        ) {
            Text(text = "8.88 ETH", style = MaterialTheme.typography.headlineLarge)
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(text = "$ 8.88", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.width(24.dp))
                Text(
                    text = "8.0 %",
                    color = Color.Green,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        }

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            Button(modifier = Modifier.weight(1f), onClick = { /*TODO*/ }) {
                Text(text = "Send")
            }
            Button(modifier = Modifier.weight(1f), onClick = { /*TODO*/ }) {
                Text(text = "Receive")
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(walletUiState.tokens, key = { it.id }) {
                TokenItemView(token = it)
            }
        }
    }

}

@ThemePreviews
@Composable
private fun UserHome_Preview() {
    EWalletTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize()
        ) {
            UserHomeContent(walletUiState = WalletUiState()) {}
        }
    }
}