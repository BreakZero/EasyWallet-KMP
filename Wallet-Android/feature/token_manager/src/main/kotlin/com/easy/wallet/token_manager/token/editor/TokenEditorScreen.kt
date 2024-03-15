package com.easy.wallet.token_manager.token.editor

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easy.wallet.token_manager.chain.editor.EditorWithLabel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun TokenEditorRoute() {
    val viewModel: TokenEditorViewModel = koinViewModel()
    val editorUiState by viewModel.tokenEditorUiState.collectAsStateWithLifecycle()
    TokenEditorScreen(editorUiState = editorUiState, onEvent = viewModel::handleEvent)
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun TokenEditorScreen(
    editorUiState: TokenEditorUiState,
    onEvent: (TokenEditorEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        bottomBar = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 12.dp),
                onClick = { onEvent(TokenEditorEvent.ClickSaved) }
            ) {
                Text(text = "Saved")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp)
        ) {
            val modifier = Modifier
                .fillMaxWidth()
            Row {
                // chain id selector
            }
            EditorWithLabel(
                modifier = modifier,
                textFieldState = editorUiState.name,
                label = "Name"
            )
            EditorWithLabel(
                modifier = modifier,
                textFieldState = editorUiState.symbol,
                label = "Symbol"
            )
            EditorWithLabel(
                modifier = modifier,
                textFieldState = editorUiState.decimals,
                label = "Decimals"
            )
            EditorWithLabel(
                modifier = modifier,
                textFieldState = editorUiState.contract,
                label = "Contract Address"
            )
            EditorWithLabel(
                modifier = modifier,
                textFieldState = editorUiState.iconUri,
                label = "Icon Uri"
            )
        }
    }
}

