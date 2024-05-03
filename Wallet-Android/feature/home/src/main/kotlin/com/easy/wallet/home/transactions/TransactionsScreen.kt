package com.easy.wallet.home.transactions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.easy.wallet.android.core.extensions.ObserveAsEvents
import com.easy.wallet.design.component.LoadingWheel
import com.easy.wallet.design.component.PullToRefreshPagingColumn
import com.easy.wallet.home.component.CollapsingToolbarWithLazyList
import com.easy.wallet.home.receive.ReceiveContentSheet
import com.easy.wallet.home.transactions.component.AmountHeaderView
import com.easy.wallet.home.transactions.component.TransactionSummaryView
import com.easy.wallet.shared.model.transaction.TransactionUiModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun TransactionsRoute(
    startToSend: (String) -> Unit,
    showSnackbar: (String) -> Unit,
    navigateUp: () -> Unit
) {
    val viewModel: TransactionsViewModel = koinViewModel()
    val transactionUiState = viewModel.transactionPager.collectAsLazyPagingItems()
    val dashboardUiState by viewModel.dashboardUiState.collectAsStateWithLifecycle()

    ObserveAsEvents(flow = viewModel.navigationEvents) {
        when (it) {
            TransactionEvent.PopBack -> navigateUp()
            is TransactionEvent.ClickSend -> startToSend(it.tokenId)
            is TransactionEvent.ShowSnackbar -> showSnackbar(it.message)
            else -> Unit
        }
    }

    TransactionsScreen(
        dashboardUiState = dashboardUiState,
        transactionPaging = transactionUiState,
        onEvent = viewModel::handleEvent
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TransactionsScreen(
    dashboardUiState: TransactionDashboardUiState,
    transactionPaging: LazyPagingItems<TransactionUiModel>,
    onEvent: (TransactionEvent) -> Unit
) {
    var showReceiveSheet by remember {
        mutableStateOf(false)
    }
    Scaffold(
        containerColor = Color.Transparent,
        contentColor = MaterialTheme.colorScheme.onBackground,
        topBar = {
            TopAppBar(
                title = { Text(text = "Transactions") },
                navigationIcon = {
                    IconButton(onClick = { onEvent(TransactionEvent.PopBack) }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors()
                    .copy(containerColor = MaterialTheme.colorScheme.inverseOnSurface)
            )
        },
        bottomBar = {
            if (dashboardUiState is TransactionDashboardUiState.Success) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    ElevatedButton(
                        modifier = Modifier.weight(1.0f),
                        onClick = { onEvent(TransactionEvent.ClickSend("")) }
                    ) {
                        Text(text = "SEND")
                    }
                    ElevatedButton(
                        modifier = Modifier.weight(1.0f),
                        colors = ButtonDefaults.elevatedButtonColors()
                            .copy(containerColor = MaterialTheme.colorScheme.onTertiaryContainer),
                        onClick = { showReceiveSheet = true }
                    ) {
                        Text(text = "RECEIVE")
                    }
                }
            }
        },
        modifier = Modifier.fillMaxSize()
    ) {
        val lazyListState = rememberLazyListState()
        CollapsingToolbarWithLazyList(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            header = { headerModifier ->
                when (dashboardUiState) {
                    TransactionDashboardUiState.Loading -> {
                        Box(
                            modifier = headerModifier.height(260.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            LoadingWheel(contentDesc = "")
                        }
                    }

                    TransactionDashboardUiState.Error -> {
                        Box(
                            modifier = headerModifier.height(260.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Button(onClick = {
                                // retry action
                            }) {
                                Text(text = "Oops... something went wrong! tap to retry")
                            }
                        }
                    }

                    is TransactionDashboardUiState.Success -> {
                        AmountHeaderView(
                            modifier = headerModifier.height(260.dp),
                            tokenInfo = dashboardUiState.basicResult,
                            balance = dashboardUiState.amount,
                            trends = dashboardUiState.trends
                        )
                    }
                }
            },
            listState = lazyListState,
            listContent = { contentModifier ->
                PullToRefreshPagingColumn(
                    modifier = contentModifier.clip(
                        RoundedCornerShape(
                            topEnd = 20.dp,
                            topStart = 20.dp
                        )
                    ),
                    lazyListState = lazyListState,
                    pagingItems = transactionPaging,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    itemKey = { index -> transactionPaging[index]!!.hash },
                    itemContainer = { transaction ->
                        TransactionSummaryView(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            transaction = transaction,
                            itemClicked = {
                                println(it.hash)
                            }
                        )
                    }
                )
            }
        )

        if (dashboardUiState is TransactionDashboardUiState.Success && showReceiveSheet) {
            ReceiveContentSheet(
                basicResult = dashboardUiState.basicResult,
                onCopy = { onEvent(TransactionEvent.ShowSnackbar(it)) },
                onDismissRequest = { showReceiveSheet = false }
            )
        }
    }
}
