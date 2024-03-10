package com.easy.wallet.token_manager.chain.manager

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBoxState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easy.wallet.android.core.extensions.ObserveAsEvents
import com.easy.wallet.design.component.SwipeToActions
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun ChainManagerRoute(
    navigateToEditor: (Long) -> Unit,
    navigateUp: () -> Unit
) {
    val viewModel: ChainManagerViewModel = koinViewModel()
    ObserveAsEvents(flow = viewModel.navigationEvents) {
        when (it) {
            is ChainManagerEvent.ClickEdit -> {
                navigateToEditor(it.id)
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
        topBar = {
            TopAppBar(title = { }, navigationIcon = {
                IconButton(onClick = navigateUp) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            },
                actions = {
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(
                            imageVector = Icons.Default.AddCircleOutline,
                            contentDescription = null
                        )
                    }
                })
        }
    ) { paddingValues ->
        val modifier = Modifier
            .fillMaxSize()
            .padding(paddingValues)
        when (uiState) {
            is ChainUiState.Loading -> {
                Box(modifier = modifier, contentAlignment = Alignment.Center) {

                }
            }

            is ChainUiState.Success -> {
                LazyColumn(
                    modifier = modifier
                ) {
                    items(uiState.chains, key = { it.id }) {
                        SwipeToActions(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(48.dp),
                            content = {
                                Text(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(48.dp),
                                    textAlign = TextAlign.Center,
                                    lineHeight = 48.sp,
                                    text = it.name
                                )
                            },
                            actions = {
                                Row {
                                    IconButton(
                                        modifier = Modifier.background(MaterialTheme.colorScheme.error),
                                        onClick = {
                                            println("clicked delete ${it.id}")
                                        }) {
                                        Icon(
                                            imageVector = Icons.Filled.Delete,
                                            contentDescription = null
                                        )
                                    }
                                    IconButton(
                                        modifier = Modifier.background(MaterialTheme.colorScheme.secondary),
                                        onClick = {
                                            println("clicked edit ${it.id}")
                                        }) {
                                        Icon(
                                            imageVector = Icons.Filled.Edit,
                                            contentDescription = null
                                        )
                                    }
                                }
                            },
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DeleteBackground(
    swipeToDismissState: SwipeToDismissBoxState
) {

}
