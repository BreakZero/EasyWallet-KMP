package com.easy.wallet.token_manager.token.editor

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easy.wallet.design.theme.ThemePreviews
import com.easy.wallet.token_manager.chain.editor.EditorWithLabel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun TokenEditorRoute(
    navigateUp: () -> Unit
) {
    val viewModel: TokenEditorViewModel = koinViewModel()
    val editorUiState by viewModel.tokenEditorUiState.collectAsStateWithLifecycle()
    val editorFields by viewModel.tokenEditorFields.collectAsStateWithLifecycle()
    TokenEditorScreen(
        editorUiState = editorUiState,
        editorFields = editorFields,
        navigateUp = navigateUp,
        onEvent = viewModel::handleEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun TokenEditorScreen(
    editorUiState: TokenEditorUiState,
    editorFields: TokenEditorFields,
    navigateUp: () -> Unit,
    onEvent: (TokenEditorEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = navigateUp) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors()
                    .copy(containerColor = MaterialTheme.colorScheme.inverseOnSurface)
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
        var isShowChainSelector by remember {
            mutableStateOf(false)
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            val modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
            Row(modifier = modifier.clickable {
                isShowChainSelector = true
            }) {
                // chain id selector
                Text(text = "In Chain")
                Text(text = editorUiState.chainName)
                Spacer(modifier = Modifier.weight(1.0f))
                Icon(imageVector = Icons.Default.KeyboardArrowRight, contentDescription = null)
            }
            EditorWithLabel(
                modifier = modifier,
                textFieldState = editorFields.name,
                label = "Name"
            )
            EditorWithLabel(
                modifier = modifier,
                textFieldState = editorFields.symbol,
                label = "Symbol"
            )
            EditorWithLabel(
                modifier = modifier,
                textFieldState = editorFields.decimals,
                label = "Decimals"
            )
            EditorWithLabel(
                modifier = modifier,
                textFieldState = editorFields.contract,
                label = "Contract Address"
            )
            EditorWithLabel(
                modifier = modifier,
                textFieldState = editorFields.iconUri,
                label = "Icon Uri"
            )
            Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
                Text(text = "Active")
                Spacer(modifier = Modifier.weight(1.0f))
                Switch(
                    checked = editorUiState.isActive,
                    onCheckedChange = { onEvent(TokenEditorEvent.OnActiveChanged(it)) }
                )
            }
        }

        if (isShowChainSelector) {
            ModalBottomSheet(onDismissRequest = { isShowChainSelector = false }) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(480.dp)
                ) {
                    items(editorUiState.localChains, key = { it.id }) {
                        Text(text = it.name, modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                onEvent(TokenEditorEvent.OnChainChanged(it.id))
                                isShowChainSelector = false
                            }
                        )
                    }
                }
            }
        }
    }
}

