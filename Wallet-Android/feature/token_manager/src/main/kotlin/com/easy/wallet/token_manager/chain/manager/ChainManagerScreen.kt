package com.easy.wallet.token_manager.chain.manager

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easy.wallet.android.core.extensions.ObserveAsEvents
import com.easy.wallet.design.component.LoadingWheel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun ChainManagerRoute(
    navigateToDetail: (Long) -> Unit,
    navigateUp: () -> Unit
) {
    val viewModel: ChainManagerViewModel = koinViewModel()
    ObserveAsEvents(flow = viewModel.navigationEvents) {
        when (it) {
            is ChainManagerEvent.ClickEdit -> {
                navigateToDetail(it.id)
            }

            is ChainManagerEvent.ClickAdd -> {
                navigateToDetail(-1L)
            }

            else -> Unit
        }
    }
    val uiState by viewModel.chainManagerUiState.collectAsStateWithLifecycle()
    ChainManagerScreen(uiState = uiState, navigateUp = navigateUp, onEvent = viewModel::handleEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ChainManagerScreen(
    uiState: ChainUiState,
    navigateUp: () -> Unit,
    onEvent: (ChainManagerEvent) -> Unit
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
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors()
                    .copy(containerColor = MaterialTheme.colorScheme.inverseOnSurface)
            )
        }
    ) { paddingValues ->
        val modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
        when (uiState) {
            is ChainUiState.Loading -> {
                Box(modifier = modifier, contentAlignment = Alignment.Center) {
                    LoadingWheel(contentDesc = "")
                }
            }

            is ChainUiState.Success -> {
                if (uiState.chains.isEmpty()) {
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Button(onClick = { onEvent(ChainManagerEvent.InitialDefaultData) }) {
                            Text(text = "Initial Default Supported Chains")
                        }
                    }
                } else {
                    LazyColumn(
                        modifier = modifier,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(uiState.chains, key = { it.id }) {
                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 16.dp),
                                onClick = { onEvent(ChainManagerEvent.ClickEdit(it.id)) }) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(vertical = 8.dp, horizontal = 12.dp)
                                ) {
                                    Text(
                                        text = it.name,
                                        style = MaterialTheme.typography.titleLarge
                                    )
                                    it.explorer?.let { explorer ->
                                        Text(text = explorer)
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
