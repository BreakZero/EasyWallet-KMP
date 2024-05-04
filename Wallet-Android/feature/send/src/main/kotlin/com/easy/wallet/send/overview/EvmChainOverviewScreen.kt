package com.easy.wallet.send.overview

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material3.Button
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.easy.wallet.design.component.DynamicAsyncImage
import com.easy.wallet.design.component.EasyGradientBackground
import com.easy.wallet.design.theme.ThemePreviews
import com.easy.wallet.design.ui.EasyWalletTheme
import com.easy.wallet.model.asset.AssetBalance
import com.easy.wallet.send.OverviewUiState
import com.easy.wallet.send.SendUiEvent
import com.easy.wallet.send.component.FeeTierBottomSheet
import com.easy.wallet.send.component.FeeTierItem
import com.easy.wallet.shared.model.fees.EthereumFee
import com.easy.wallet.shared.model.fees.FeeLevel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EvmChainOverviewScreen(
    assetBalance: AssetBalance,
    overviewUiState: OverviewUiState,
    onEvent: (SendUiEvent) -> Unit
) {
    Scaffold(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = {
            TopAppBar(
                title = { Text(text = "Overview") },
                navigationIcon = {
                    IconButton(onClick = { onEvent(SendUiEvent.NavigateBack) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = ""
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
            )
        },
        bottomBar = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                onClick = { onEvent(SendUiEvent.OnSigningTransaction(null)) }
            ) {
                Text(text = "Next")
            }
        }
    ) {

        var showFeeTiers by remember {
            mutableStateOf(false)
        }
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
                DynamicAsyncImage(
                    modifier = Modifier.size(48.dp),
                    imageUrl = assetBalance.logoURI, contentDescription = null
                )
                Column {
                    Text(
                        text = "${overviewUiState.amount} ${assetBalance.symbol}",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    // waiting rate api
                    Text(text = "= $--")
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
                    text = assetBalance.address,
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
                    text = overviewUiState.destination
                )
                Spacer(modifier = Modifier.weight(1.0f))
                Icon(
                    imageVector = Icons.Default.ContentCopy,
                    contentDescription = null,
                    tint = labelColor
                )
            }

            HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))
            overviewUiState.selectedFee?.let {
                FeeTierItem(
                    modifier = basicModifier
                        .clip(RoundedCornerShape(12.dp))
                        .clickable { showFeeTiers = true },
                    data = it,
                    onClick = { showFeeTiers = true }
                )
            }
        }
        if (overviewUiState.fees.isNotEmpty() && showFeeTiers) {
            FeeTierBottomSheet(
                fees = overviewUiState.fees,
                onItemClick = {
                    onEvent(SendUiEvent.OnFeeChanged(it))
                },
                dismiss = { showFeeTiers = false }
            )
        }
    }
}

@ThemePreviews
@Composable
private fun Overview_Preview() {
    EasyWalletTheme {
        EasyGradientBackground {
            EvmChainOverviewScreen(
                assetBalance = AssetBalance.mockForPreview,
                overviewUiState = OverviewUiState(
                    "", "", fees = listOf(
                        EthereumFee(FeeLevel.Low, "", "", ""),
                        EthereumFee(FeeLevel.Fast, "", "", "")
                    )
                ),
                onEvent = {}
            )
        }
    }
}
