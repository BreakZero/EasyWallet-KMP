package com.easy.wallet.home.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.easy.wallet.design.theme.ThemePreviews
import com.easy.wallet.design.ui.EWalletTheme
import com.easy.wallet.home.HomeEvent
import com.easy.wallet.home.HomeUiState

@Composable
internal fun UserHomeContent(
    modifier: Modifier = Modifier,
    uiState: HomeUiState.WalletUiState,
    onEvent: (HomeEvent) -> Unit
) {
    Column(
        modifier = modifier,
    ) {
        DashboardView(
            modifier = Modifier.fillMaxWidth(),
            onEvent = onEvent
        )
        LazyColumn(
            modifier = Modifier
                .padding(top = 12.dp)
                .fillMaxSize()
                .shadow(elevation = 3.dp, shape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp)),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(uiState.tokens, key = { it.token.id }) {
                TokenItemView(extraToken = it, onEvent = onEvent)
            }
        }
    }
}

@ThemePreviews
@Composable
private fun UserHome_Preview() {
    EWalletTheme {
        Surface(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            UserHomeContent(uiState = HomeUiState.WalletUiState(emptyList())) {}
        }
    }
}
