package com.easy.wallet.send.amount

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.Backspace
import androidx.compose.material.icons.filled.Wallet
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import com.easy.wallet.model.TokenBasicResult
import com.easy.wallet.send.AmountUiState
import com.easy.wallet.send.SendUiEvent
import com.easy.wallet.send.SendingBasicUiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun EvmChainSendAmountScreen(
    basicInfo: SendingBasicUiState.PrepBasicInfo,
    amountUiState: AmountUiState,
    onEvent: (SendUiEvent) -> Unit
) {
    Scaffold(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = {
            TopAppBar(
                title = { Text(text = "Send") },
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
                enabled = !amountUiState.insufficientBalance,
                onClick = { onEvent(SendUiEvent.BuildTransactionPlan) }
            ) {
                Text(text = "Next")
            }
        }
    ) { paddingValues ->
        val color = if (amountUiState.insufficientBalance) MaterialTheme.colorScheme.error else Color.Unspecified
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            Box(
                modifier = Modifier
                    .weight(1.0f)
                    .fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${amountUiState.enterAmount} ${basicInfo.tokenInfo.symbol}",
                    color = color,
                    style = MaterialTheme.typography.displaySmall
                )
            }
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(imageVector = Icons.Default.Wallet, contentDescription = null)
                    Column(modifier = Modifier.weight(1.0f)) {
                        Text(text = "Balance")
                        Text(text = "${basicInfo.balance} ${basicInfo.tokenInfo.symbol}")
                    }
                    Button(onClick = { onEvent(SendUiEvent.MaxAmount) }) {
                        Text(text = "Max")
                    }
                }
            }
            LazyVerticalGrid(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp),
                columns = GridCells.Fixed(3),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items((1..9).toList(), key = { it }) {
                    Button(onClick = {
                        onEvent(SendUiEvent.EnterDigital(it.toString()))
                    }) {
                        Text(text = "$it")
                    }
                }
                item(key = ".") {
                    Button(onClick = { onEvent(SendUiEvent.EnterDigital(".")) }) {
                        Text(text = ".")
                    }
                }
                item(key = "0") {
                    Button(onClick = { onEvent(SendUiEvent.EnterDigital("0")) }) {
                        Text(text = "0")
                    }
                }
                item(key = "backspace") {
                    Button(onClick = {
                        onEvent(SendUiEvent.Backspace)
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Outlined.Backspace,
                            contentDescription = null
                        )
                    }
                }
            }
        }
    }
}

@ThemePreviews
@Composable
private fun EvmChainSendAmount_Preview() {
    EasyWalletTheme {
        EasyGradientBackground {
            EvmChainSendAmountScreen(
                SendingBasicUiState.PrepBasicInfo(
                    tokenInfo = TokenBasicResult("", "", 18, "", "", null, "Ethereum", "1"),
                    balance = "8.00"
                ),
                amountUiState = AmountUiState(""),
                {}
            )
        }
    }
}
