package com.easy.wallet.home.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.easy.wallet.design.component.EasyGradientBackground
import com.easy.wallet.design.theme.ThemePreviews
import com.easy.wallet.design.ui.EasyWalletTheme
import com.easy.wallet.home.HomeEvent
import com.easy.wallet.home.HomeUiState

@Composable
internal fun UserHomeContent(
    modifier: Modifier = Modifier,
    walletUiState: HomeUiState.WalletUiState,
    onEvent: (HomeEvent) -> Unit
) {
    val toolbarHeightRange = with(LocalDensity.current) {
        0.dp.roundToPx()..248.dp.roundToPx()
    }
    val lazyListState = rememberLazyListState()
    CollapsingToolbarWithLazyList(
        modifier = modifier,
        listState = lazyListState,
        toolbarHeightRange = toolbarHeightRange,
        header = { headerModifier ->
            DashboardView(
                modifier = headerModifier
                    .height(200.dp)
            )
        },
        listContent = { contentModifier ->
            LazyColumn(
                modifier = contentModifier
                    .clip(RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp)),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                state = lazyListState
            ) {
                items(walletUiState.tokens, key = { it.token.id }) {
                    TokenItemView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        extraToken = it,
                        onEvent = onEvent
                    )
                }
            }
        }
    )
}

@ThemePreviews
@Composable
private fun UserHome_Preview() {
    EasyWalletTheme {
        EasyGradientBackground(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            UserHomeContent(walletUiState = HomeUiState.WalletUiState(emptyList())) {}
        }
    }
}
