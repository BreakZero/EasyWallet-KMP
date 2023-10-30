package com.easy.wallet.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.LastBaseline
import androidx.compose.ui.unit.dp
import com.easy.wallet.design.theme.ThemePreviews
import com.easy.wallet.design.ui.EWalletTheme
import com.easy.wallet.home.HomeEvent
import com.easy.wallet.home.HomeUiState

@Composable
internal fun UserHomeContent(
    modifier: Modifier = Modifier,
    uiState: HomeUiState.WalletUiState,
    onEvent: (HomeEvent) -> Unit
) {
    Column(
        modifier = modifier,
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(onClick = {
                onEvent(HomeEvent.ClickSettings)
            }) {
                Icon(
                    modifier = Modifier
                        .size(48.dp)
                        .padding(4.dp),
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = "",
                )
            }
            Text(
                text = "Account Name",
                style = MaterialTheme.typography.titleSmall
            )
        }

        Text(
            modifier = Modifier.padding(top = 8.dp),
            text = "8.88 ETH",
            style = MaterialTheme.typography.headlineLarge,
        )

        Row(
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                space = 24.dp,
                alignment = Alignment.CenterHorizontally,
            ),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = CircleShape,
                    ),
                ) {
                    Icon(imageVector = Icons.Default.ArrowUpward, contentDescription = null)
                }
                Text(text = "Send")
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                IconButton(
                    onClick = { /*TODO*/ },
                    modifier = Modifier.background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = CircleShape,
                    ),
                ) {
                    Icon(imageVector = Icons.Default.ArrowDownward, contentDescription = null)
                }
                Text(text = "Receive")
            }
        }
        LazyColumn(
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(uiState.tokens, key = { it.token.id }) {
                TokenItemView(extraToken = it, onEvent = onEvent)
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
                .fillMaxSize(),
        ) {
            UserHomeContent(uiState = HomeUiState.WalletUiState(emptyList())) {}
        }
    }
}
