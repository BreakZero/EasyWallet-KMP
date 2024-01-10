package com.easy.wallet.home.component

import androidx.compose.animation.core.FloatExponentialDecaySpec
import androidx.compose.animation.core.animateDecay
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Velocity
import androidx.compose.ui.unit.dp
import com.easy.wallet.design.theme.ThemePreviews
import com.easy.wallet.design.ui.EWalletTheme
import com.easy.wallet.home.HomeEvent
import com.easy.wallet.home.HomeUiState
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.launch

@Composable
internal fun UserHomeContent(
    modifier: Modifier = Modifier,
    walletUiState: HomeUiState.WalletUiState,
    onEvent: (HomeEvent) -> Unit
) {
    val toolbarHeightRange = with(LocalDensity.current) {
        0.dp.roundToPx()..260.dp.roundToPx()
    }
    val listState = rememberLazyListState()
    val toolbarState = rememberCollapsingToolbarState(toolbarHeightRange)
    val scope = rememberCoroutineScope()

    val nestedScrollConnection = remember {
        object : NestedScrollConnection {
            override fun onPreScroll(available: Offset, source: NestedScrollSource): Offset {
                toolbarState.scrollTopLimitReached =
                    listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset == 0
                toolbarState.scrollOffset = toolbarState.scrollOffset - available.y

                println("===== progress: ${toolbarState.progress}")
                return Offset(0f, toolbarState.consumed)
            }

            override suspend fun onPostFling(consumed: Velocity, available: Velocity): Velocity {
                if (available.y > 0) {
                    scope.launch {
                        animateDecay(
                            initialValue = toolbarState.height + toolbarState.offset,
                            initialVelocity = available.y,
                            animationSpec = FloatExponentialDecaySpec()
                        ) { value, _ ->
                            toolbarState.scrollTopLimitReached =
                                listState.firstVisibleItemIndex == 0 && listState.firstVisibleItemScrollOffset == 0
                            toolbarState.scrollOffset =
                                toolbarState.scrollOffset - (value - (toolbarState.height + toolbarState.offset))
                            if (toolbarState.scrollOffset == 0f) scope.coroutineContext.cancelChildren()
                        }
                    }
                }
                return super.onPostFling(consumed, available)
            }
        }
    }

    Box(
        modifier = modifier.nestedScroll(nestedScrollConnection),
    ) {
        DashboardView(
            modifier = Modifier
                .fillMaxWidth()
                .height(260.dp)
                .graphicsLayer {
                    translationY =
                        toolbarHeightRange.last * (toolbarState.progress * toolbarState.progress - 1.0f)
                }
        )
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .graphicsLayer { translationY = toolbarState.height + toolbarState.offset }
                .shadow(
                    elevation = 2.dp,
                    shape = RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp)
                )
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .pointerInput(Unit) {
                    detectTapGestures(
                        onPress = { scope.coroutineContext.cancelChildren() }
                    )
                },
            verticalArrangement = Arrangement.spacedBy(12.dp),
            state = listState
        ) {
            items(walletUiState.tokens, key = { it.token.id }) {
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
            UserHomeContent(walletUiState = HomeUiState.WalletUiState(emptyList())) {}
        }
    }
}
