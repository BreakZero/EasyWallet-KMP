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
import com.easy.wallet.design.component.PullToRefreshBox
import com.easy.wallet.design.theme.ThemePreviews
import com.easy.wallet.design.ui.EasyWalletTheme
import com.easy.wallet.home.WalletEvent
import com.easy.wallet.home.WalletUiState
import com.easy.wallet.shared.model.AllAssetDashboardInformation

@Composable
internal fun UserHomeContent(
    modifier: Modifier = Modifier,
    isRefreshing: Boolean,
    walletUiState: WalletUiState.WalletUiState,
    onEvent: (WalletEvent) -> Unit
) {
    val toolbarHeightRange = with(LocalDensity.current) {
        0.dp.roundToPx()..248.dp.roundToPx()
    }
    val lazyListState = rememberLazyListState()
    PullToRefreshBox(
        modifier = modifier,
        isRefreshing = isRefreshing,
        onRefresh = {
            onEvent(WalletEvent.OnRefreshing)
        }
    ) {
        CollapsingToolbarWithLazyList(
            modifier = Modifier.fillMaxSize(),
            listState = lazyListState,
            toolbarHeightRange = toolbarHeightRange,
            header = { headerModifier ->
                DashboardView(
                    modifier = headerModifier
                        .height(200.dp),
                    fiatBalance = walletUiState.dashboard.fiatBalance,
                    fiatSymbol = walletUiState.dashboard.fiatSymbol
                )
            },
            listContent = { contentModifier ->
                LazyColumn(
                    modifier = contentModifier
                        .clip(RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp)),
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    state = lazyListState
                ) {
                    items(
                        walletUiState.dashboard.assetBalances,
                        key = { "${it.id}-${it.platform.id}" }
                    ) {
                        TokenItemView(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            assetBalance = it,
                            onEvent = onEvent
                        )
                    }
                }
            }
        )
    }
}

@ThemePreviews
@Composable
private fun UserHome_Preview() {
    EasyWalletTheme {
        EasyGradientBackground(
            modifier = Modifier
                .fillMaxSize(),
        ) {
            UserHomeContent(
                isRefreshing = false,
                walletUiState = WalletUiState.WalletUiState(
                    AllAssetDashboardInformation(
                        fiatSymbol = "USD",
                        fiatBalance = "888.88",
                        assetBalances = emptyList()
                    )
                ),
                onEvent = {}
            )
        }
    }
}
