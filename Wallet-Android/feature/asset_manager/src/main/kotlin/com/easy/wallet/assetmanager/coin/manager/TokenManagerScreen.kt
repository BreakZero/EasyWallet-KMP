package com.easy.wallet.assetmanager.coin.manager

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircleOutline
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easy.wallet.android.core.extensions.ObserveAsEvents
import com.easy.wallet.design.component.DynamicAsyncImage
import com.easy.wallet.design.component.LoadingWheel
import com.easy.wallet.model.asset.BasicCoin
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun TokenManagerRoute(
    navigateToEditor: (String?) -> Unit,
    navigateUp: () -> Unit
) {
    val viewModel: TokenManagerViewModel = koinViewModel()
    ObserveAsEvents(flow = viewModel.navigationEvents) {
        when (it) {
            is TokenManagerEvent.ClickAdd -> {
                navigateToEditor(null)
            }

            is TokenManagerEvent.ClickEdit -> navigateToEditor(it.id)
            is TokenManagerEvent.ClickPopBack -> navigateUp()
            else -> Unit
        }
    }
    val uiState by viewModel.tokenManagerUiState.collectAsStateWithLifecycle()
    TokenManagerScreen(uiState = uiState, onEvent = viewModel::handleEvent)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TokenManagerScreen(
    uiState: TokenManagerUiState,
    onEvent: (TokenManagerEvent) -> Unit
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = {
            TopAppBar(
                title = { },
                navigationIcon = {
                    IconButton(onClick = { onEvent(TokenManagerEvent.ClickPopBack) }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                actions = {
                    IconButton(onClick = { onEvent(TokenManagerEvent.ClickEdit("")) }) {
                        Icon(
                            imageVector = Icons.Default.AddCircleOutline,
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
            .padding(16.dp)
        when (uiState) {
            is TokenManagerUiState.Loading -> {
                Box(modifier = modifier, contentAlignment = Alignment.Center) {
                    LoadingWheel(contentDesc = "Loading Tokens")
                }
            }

            is TokenManagerUiState.Success -> {
                LazyColumn(
                    modifier = modifier
                ) {
                    items(uiState.coins, key = { it.id }) {
                        TokenInformationView(modifier = Modifier.fillMaxWidth(), coin = it) {

                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TokenInformationView(
    modifier: Modifier,
    coin: BasicCoin,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        DynamicAsyncImage(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape),
            imageUrl = coin.logoURI,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.wrapContentHeight(align = Alignment.CenterVertically)) {
            Text(text = coin.name)
            Text(text = coin.symbol)
        }
        Spacer(modifier = Modifier.weight(1.0f))
        Switch(checked = false, onCheckedChange = onCheckedChange)
    }
}
