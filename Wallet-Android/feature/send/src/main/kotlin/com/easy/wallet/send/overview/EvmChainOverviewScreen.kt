package com.easy.wallet.send.overview

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.easy.wallet.design.component.DynamicAsyncImage
import com.easy.wallet.design.component.EasyGradientBackground
import com.easy.wallet.design.theme.ThemePreviews
import com.easy.wallet.design.ui.EasyWalletTheme
import com.easy.wallet.model.TokenBasicResult
import com.easy.wallet.send.SendUiEvent
import com.easy.wallet.send.SendUiState
import com.easy.wallet.shared.model.fees.EthereumFee
import com.easy.wallet.shared.model.fees.FeeLevel
import com.easy.wallet.shared.model.fees.FeeModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EvmChainOverviewScreen(
    uiState: SendUiState.Success,
    fees: List<FeeModel>,
    onEvent: (SendUiEvent) -> Unit
) {
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
        },
        bottomBar = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                onClick = { onEvent(SendUiEvent.OnSigningTransaction(uiState.tokenInfo.chainIdHex)) }
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
                    imageUrl = uiState.tokenInfo.iconUri, contentDescription = null
                )
                Column {
                    Text(
                        text = "${uiState.amount} ${uiState.tokenInfo.symbol}",
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
                    text = uiState.tokenInfo.address,
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
                    text = uiState.recipient
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
                modifier = basicModifier.clickable { showFeeTiers = true }, text = "Fees",
                style = MaterialTheme.typography.labelMedium,
                color = labelColor
            )
        }
        if (fees.isNotEmpty() && showFeeTiers) {
            FeeTierBottomSheet(fees = fees) {
                showFeeTiers = false
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FeeTierBottomSheet(
    modifier: Modifier = Modifier,
    fees: List<FeeModel>,
    onDismissRequest: () -> Unit
) {
    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    ModalBottomSheet(
        modifier = modifier,
        sheetState = bottomSheetState,
        onDismissRequest = onDismissRequest
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(bottom = 24.dp)
        ) {
            items(fees, key = { it.feeLevel }) {
                Text(text = "fee: ${it.feeLevel}")
            }
        }
    }
}

@ThemePreviews
@Composable
private fun Overview_Preview() {
    EasyWalletTheme {
        EasyGradientBackground {
            EvmChainOverviewScreen(
                SendUiState.Success(
                    tokenInfo = TokenBasicResult("", "", 18, "", "", null, "Ethereum", ""),
                    balance = "8.00",
                    recipient = "",
                    amount = ""
                ),
                fees = listOf(
                    EthereumFee(FeeLevel.Low, "", "", ""),
                    EthereumFee(FeeLevel.Fast, "", "", "")
                ),
                onEvent = {}
            )
        }
    }
}
