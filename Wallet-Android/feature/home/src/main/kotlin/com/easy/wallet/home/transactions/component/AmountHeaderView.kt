package com.easy.wallet.home.transactions.component

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
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
import androidx.compose.ui.unit.dp
import com.easy.wallet.design.theme.ThemePreviews
import com.easy.wallet.design.ui.EasyWalletTheme

@Composable
internal fun AmountHeaderView(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.5f)
                .aspectRatio(1f)
                .border(2.dp, color = MaterialTheme.colorScheme.onBackground, shape = CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(text = "8.066", style = MaterialTheme.typography.titleLarge)
                Text(text = "¥ 29.72")
            }
        }
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(
                space = 12.dp,
                alignment = Alignment.CenterHorizontally,
            ),
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(
                    modifier = Modifier.background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = CircleShape,
                    ),
                    onClick = { /*TODO*/ },
                ) {
                    Icon(imageVector = Icons.Default.ArrowUpward, contentDescription = "")
                }
                Text(text = "Send")
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(
                    modifier = Modifier.background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = CircleShape,
                    ),
                    onClick = { /*TODO*/ },
                ) {
                    Icon(imageVector = Icons.Default.ArrowDownward, contentDescription = "")
                }
                Text(text = "Receive")
            }

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                IconButton(
                    modifier = Modifier.background(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = CircleShape,
                    ),
                    onClick = { /*TODO*/ },
                ) {
                    Icon(imageVector = Icons.Default.ArrowUpward, contentDescription = "")
                }
                Text(text = "Trade")
            }
        }
    }
}

@ThemePreviews
@Composable
private fun AmountHeader_Preview() {
    EasyWalletTheme {
        Surface {
            AmountHeaderView()
        }
    }
}
