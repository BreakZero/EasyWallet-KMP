package com.easy.wallet.token_manager.chain.editor

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text2.BasicTextField2
import androidx.compose.foundation.text2.input.InputTransformation
import androidx.compose.foundation.text2.input.TextFieldLineLimits
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easy.wallet.android.core.extensions.ObserveAsEvents
import com.easy.wallet.design.theme.ThemePreviews
import com.easy.wallet.design.ui.EasyWalletTheme
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun ChainEditorRoute(
    navigateUp: () -> Unit
) {
    val viewModel: ChainEditorViewModel = koinViewModel()

    ObserveAsEvents(flow = viewModel.navigationEvents) {
        if (it == ChainEditorUiEvent.NavigateUp) {
            navigateUp()
        }
    }

    val chainEditorUiState by viewModel.chainEditorUiState.collectAsStateWithLifecycle()
    ChainEditorScreen(
        chainEditorUiState = chainEditorUiState,
        onEvent = viewModel::handleEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
private fun ChainEditorScreen(
    chainEditorUiState: ChainEditorUiState,
    onEvent: (ChainEditorUiEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = {
            TopAppBar(title = { }, navigationIcon = {
                IconButton(onClick = { onEvent(ChainEditorUiEvent.NavigateUp) }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            }, colors = TopAppBarDefaults.topAppBarColors()
                .copy(containerColor = MaterialTheme.colorScheme.inverseOnSurface)
            )
        },
        bottomBar = {
            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .padding(bottom = 12.dp),
                onClick = { onEvent(ChainEditorUiEvent.OnSavedClick) }
            ) {
                Text(text = "Save")
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            val modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)

            EditorWithLabel(
                modifier = modifier,
                textFieldState = chainEditorUiState.name,
                label = "Name"
            )
            EditorWithLabel(
                modifier = modifier,
                textFieldState = chainEditorUiState.rpcUrl,
                label = "RPC Url"
            )
            EditorWithLabel(
                modifier = modifier,
                textFieldState = chainEditorUiState.chainId,
                label = "Chain ID"
            )
            EditorWithLabel(
                modifier = modifier,
                textFieldState = chainEditorUiState.website,
                label = "Website"
            )
            EditorWithLabel(
                modifier = modifier,
                textFieldState = chainEditorUiState.explorer,
                label = "Explorer"
            )
        }
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
internal fun EditorWithLabel(
    modifier: Modifier = Modifier,
    inputTransformation: InputTransformation? = null,
    textFieldState: TextFieldState,
    label: String
) {
    Row(
        modifier = modifier.padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(style = MaterialTheme.typography.labelMedium, text = label)
        BasicTextField2(
            modifier = Modifier
                .weight(1.0f)
                .padding(horizontal = 8.dp),
            textStyle = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.End),
            inputTransformation = inputTransformation,
            lineLimits = TextFieldLineLimits.SingleLine,
            state = textFieldState
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@ThemePreviews
@Composable
private fun Editor_Preview() {
    EasyWalletTheme {
        Surface(
            modifier = Modifier.fillMaxWidth()
        ) {
            ChainEditorScreen(ChainEditorUiState(), onEvent = {})
        }
    }
}
