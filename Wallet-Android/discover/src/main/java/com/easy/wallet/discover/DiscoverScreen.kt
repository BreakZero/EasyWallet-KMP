package com.easy.wallet.discover

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easy.wallet.design.component.LoadingWheel
import com.easy.wallet.discover.component.NewItemView
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun DiscoverRoute() {
    val viewModel: DiscoverViewModel = koinViewModel()
    val uiState by viewModel.discoverUState.collectAsStateWithLifecycle()
    DiscoverScreen(uiState = uiState)
}

@Composable
fun DiscoverScreen(
    uiState: DiscoverUiState
) {
    println(uiState)
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        when (uiState) {
            is DiscoverUiState.Loading -> {
                LoadingWheel(contentDesc = "")
            }

            is DiscoverUiState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(uiState.news, key = { it.hash }) { new ->
                        NewItemView(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            new = new
                        )
                    }
                }
            }

            else -> {
                Text(text = "error...")
            }
        }
    }
}