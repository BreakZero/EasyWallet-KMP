package com.easy.wallet.send.destination

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.rememberTextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.ElevatedButton
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

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun EvmChainDestinationScreen() {
    Scaffold(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = {
            TopAppBar(
                title = { Text(text = "Send To") },
                navigationIcon = {
                    IconButton(onClick = {  }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = ""
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent),
            )
        }
    ) { paddingValues ->
        val textFieldState = rememberTextFieldState()
        Column(
            modifier = Modifier.padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            BasicTextField2(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp, vertical = 16.dp),
                state = textFieldState,
                textStyle = MaterialTheme.typography.displaySmall,
                decorator = {
                    if (textFieldState.text.isBlank()) {
                        Text(
                            text = "enter address",
                            style = MaterialTheme.typography.displaySmall,
                            color = Color.LightGray
                        )
                    }
                }
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End)
            ) {
                ElevatedButton(onClick = { /*TODO*/ }) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.QrCodeScanner, contentDescription = null)
                        Text(text = "Scan")
                    }
                }
                ElevatedButton(onClick = { /*TODO*/ }) {
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(imageVector = Icons.Default.ContentCopy, contentDescription = null)
                        Text(text = "Paste")
                    }
                }
            }
        }
    }
}

@ThemePreviews
@Composable
private fun EvmChainDestination_Preview() {
    EasyWalletTheme {
        EasyGradientBackground {
            EvmChainDestinationScreen()
        }
    }
}
