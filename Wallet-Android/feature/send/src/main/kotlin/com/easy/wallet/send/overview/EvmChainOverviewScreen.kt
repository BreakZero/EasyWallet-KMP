package com.easy.wallet.send.overview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.easy.wallet.design.component.EasyGradientBackground
import com.easy.wallet.design.theme.ThemePreviews
import com.easy.wallet.design.ui.EasyWalletTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EvmChainOverviewScreen() {
    Scaffold(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = {
            TopAppBar(
                title = { Text(text = "Overview") },
                navigationIcon = {
                    IconButton(onClick = { }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = ""
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
            )
        }
    ) {
        val labelColor = LocalContentColor.current.copy(alpha = 0.5f)
        Column(
            modifier = Modifier.padding(it),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            val basicModifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
            Text(
                modifier = basicModifier,
                text = "Send",
                style = MaterialTheme.typography.labelMedium,
                color = labelColor
            )
            Row(
                modifier = basicModifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Icon(imageVector = Icons.Default.Home, contentDescription = null)
                Column {
                    Text(text = "0.00288 ETH", style = MaterialTheme.typography.headlineMedium)
                    Text(text = "= $200")
                }
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            Text(
                modifier = basicModifier,
                text = "Sender",
                style = MaterialTheme.typography.labelMedium,
                color = labelColor
            )
            Row(modifier = basicModifier) {
                Text(
                    modifier = Modifier.fillMaxWidth(0.6f),
                    text = "0x81080a7e991bcdddba8c2302a70f45d6bd369ab5",
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.weight(1.0f))
                Icon(
                    imageVector = Icons.Default.ContentCopy,
                    contentDescription = null,
                    tint = labelColor
                )
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            Text(
                modifier = basicModifier,
                text = "Recipient",
                style = MaterialTheme.typography.labelMedium,
                color = labelColor
            )
            Row(modifier = basicModifier) {
                Text(
                    modifier = Modifier.fillMaxWidth(0.6f),
                    text = "0x81080a7e991bcdddba8c2302a70f45d6bd369ab5"
                )
                Spacer(modifier = Modifier.weight(1.0f))
                Icon(
                    imageVector = Icons.Default.ContentCopy,
                    contentDescription = null,
                    tint = labelColor
                )
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            Text(
                modifier = basicModifier, text = "Fees",
                style = MaterialTheme.typography.labelMedium,
                color = labelColor
            )
        }
    }
}

@ThemePreviews
@Composable
private fun Overview_Preview() {
    EasyWalletTheme {
        EasyGradientBackground {
            EvmChainOverviewScreen()
        }
    }
}
