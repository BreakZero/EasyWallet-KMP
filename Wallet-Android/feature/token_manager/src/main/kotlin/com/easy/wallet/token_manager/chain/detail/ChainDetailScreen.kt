package com.easy.wallet.token_manager.chain.detail

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
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
import com.easy.wallet.design.component.LoadingWheel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun ChainDetailRoute(
    navigateUp: () -> Unit
) {
    val viewModel: ChainDetailViewModel = koinViewModel()
    val uiState by viewModel.chainInfoUiState.collectAsStateWithLifecycle()
    ChainDetailScreen(uiState = uiState, navigateUp = navigateUp)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ChainDetailScreen(
    uiState: ChainDetailUiState,
    navigateUp: () -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = {
            TopAppBar(title = { }, navigationIcon = {
                IconButton(onClick = { navigateUp() }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            }, colors = TopAppBarDefaults.topAppBarColors()
                .copy(containerColor = MaterialTheme.colorScheme.inverseOnSurface)
            )
        }
    ) { paddingValues ->
        val modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
            .padding(horizontal = 16.dp)
        when (uiState) {
            ChainDetailUiState.Loading -> {
                Box(modifier = modifier, contentAlignment = Alignment.Center) {
                    LoadingWheel(contentDesc = "")
                }
            }

            is ChainDetailUiState.Error -> {
                Box(modifier = modifier, contentAlignment = Alignment.Center) {
                    Text(text = uiState.error)
                }
            }

            is ChainDetailUiState.Success -> {
                Column(
                    modifier = modifier,
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    val itemRowModifier = Modifier
                        .fillMaxWidth()
                    ItemRow(
                        modifier = itemRowModifier,
                        label = "Name",
                        value = uiState.assetPlatform.shortName
                    )

                    uiState.assetPlatform.network?.rpcUrl?.let {
                        ItemRow(
                            modifier = itemRowModifier,
                            label = "RPC Url",
                            value = it
                        )
                    }
                    uiState.assetPlatform.chainIdentifier?.let {
                        ItemRow(
                            modifier = itemRowModifier,
                            label = "Chain ID",
                            value = it
                        )
                    }


                    uiState.assetPlatform.network?.explorerUrl?.let {
                        ItemRow(
                            modifier = itemRowModifier,
                            label = "Explorer",
                            value = it
                        )
                    }
                }
            }
        }
    }
}

@Composable
private fun ItemRow(
    modifier: Modifier,
    label: String,
    value: String,
    itemClick: (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .clickable {
                itemClick?.let { it() }
            }
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(24.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(style = MaterialTheme.typography.labelMedium, text = label)
        Text(
            modifier = Modifier
                .weight(1.0f)
                .padding(horizontal = 8.dp),
            style = MaterialTheme.typography.bodyLarge.copy(textAlign = TextAlign.End),
            maxLines = 1,
            text = value
        )
    }
}
