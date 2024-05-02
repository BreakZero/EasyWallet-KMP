package com.easy.wallet.send.destination

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.Button
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.unit.dp
import com.easy.wallet.design.component.EasyGradientBackground
import com.easy.wallet.design.component.LoadingWheel
import com.easy.wallet.design.theme.ThemePreviews
import com.easy.wallet.design.ui.EasyWalletTheme
import com.easy.wallet.send.DestinationUiState
import com.easy.wallet.send.SendUiEvent
import com.easy.wallet.send.SendUiState
import com.easy.wallet.send.navigation.sendAmountRoute

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
internal fun EvmChainDestinationScreen(
    uiState: SendUiState,
    destinationUiState: DestinationUiState,
    textFieldState: TextFieldState,
    onEvent: (SendUiEvent) -> Unit
) {
    val clipboardManager = LocalClipboardManager.current
    Scaffold(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = {
            TopAppBar(
                title = { Text(text = "Send To") },
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
                enabled = destinationUiState == DestinationUiState.Success,
                onClick = { onEvent(SendUiEvent.NavigateTo(sendAmountRoute)) }
            ) {
                Text(text = "Next")
            }
        }
    ) { paddingValues ->
        val modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
        when (uiState) {
            SendUiState.Error -> {
                Box(modifier = modifier.clickable { onEvent(SendUiEvent.NavigateBack) }) {
                    Text(text = "Tap to back...")
                }
            }

            SendUiState.Loading -> {
                Box(modifier = modifier, contentAlignment = Alignment.Center) {
                    LoadingWheel(contentDesc = "Loading")
                }
            }

            is SendUiState.PrepBasicInfo -> {
                Column(
                    modifier = modifier,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    BasicTextField2(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        state = textFieldState,
                        textStyle = MaterialTheme.typography.displaySmall,
                        decorator = { innerTextField ->
                            if (textFieldState.text.isBlank()) {
                                Text(
                                    text = "enter address",
                                    style = MaterialTheme.typography.displaySmall,
                                    color = LocalContentColor.current.copy(alpha = 0.6f)
                                )
                            }
                            innerTextField()
                        }
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End)
                    ) {
                        ElevatedButton(onClick = {  }) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.QrCodeScanner,
                                    contentDescription = null
                                )
                                Text(text = "Scan")
                            }
                        }
                        ElevatedButton(onClick = {
                            clipboardManager.getText()?.let {
                                onEvent(SendUiEvent.DestinationChanged(it.text))
                            }
                        }) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ContentCopy,
                                    contentDescription = null
                                )
                                Text(text = "Paste")
                            }
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@ThemePreviews
@Composable
private fun EvmChainDestination_Preview() {
    EasyWalletTheme {
        EasyGradientBackground {
            EvmChainDestinationScreen(
                SendUiState.Loading,
                DestinationUiState.Success,
                TextFieldState(""),
                {}
            )
        }
    }
}
