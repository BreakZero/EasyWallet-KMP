package com.easy.wallet.marketplace

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.easy.wallet.data.nft.model.OpenSeaNftDto
import com.easy.wallet.design.component.DynamicAsyncImage
import org.koin.androidx.compose.koinViewModel

@Composable
fun MarketplaceRoute() {
    val viewModel: MarketplaceViewModel = koinViewModel()
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    MarketplaceScreen(uiState = uiState)
}

@Composable
internal fun MarketplaceScreen(
    uiState: List<OpenSeaNftDto>
) {
    LazyVerticalGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        columns = GridCells.Fixed(3),
        verticalArrangement = Arrangement.spacedBy(12.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(uiState, key = { "${it.contract}-${it.identifier}" }) { nft ->
            DynamicAsyncImage(
                imageUrl = nft.imageUrl,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(12.dp))
                    .aspectRatio(1.0f)
            )
        }
    }
}