package com.easy.wallet.home.transactions

import androidx.compose.foundation.background
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.easy.wallet.android.core.extensions.ObserveAsEvents
import com.easy.wallet.design.component.DefaultPagingStateColumn
import com.easy.wallet.design.component.LoadingWheel
import com.easy.wallet.home.component.CollapsingToolbarWithLazyList
import com.easy.wallet.home.transactions.component.AmountHeaderView
import com.easy.wallet.home.transactions.component.TransactionSummaryView
import com.easy.wallet.shared.model.transaction.TransactionUiModel
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun TransactionsRoute() {
    val viewModel: TransactionsViewModel = koinViewModel()
    val transactionUiState = viewModel.transactionPager.collectAsLazyPagingItems()
    val dashboardUiState by viewModel.dashboardUiState.collectAsStateWithLifecycle()

    ObserveAsEvents(flow = viewModel.navigationEvents) {
        when (it) {
            is TransactionEvent.ClickReceive -> TODO()
            is TransactionEvent.ClickSend -> TODO()
        }
    }

    TransactionsScreen(dashboardUiState = dashboardUiState, transactionPaging = transactionUiState)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TransactionsScreen(
    dashboardUiState: TransactionDashboardUiState,
    transactionPaging: LazyPagingItems<TransactionUiModel>
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Transactions") }, navigationIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            })
        },
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Button(
                    modifier = Modifier.weight(1.0f),
                    onClick = { /*TODO*/ }
                ) {
                    Text(text = "SEND")
                }
                Button(
                    modifier = Modifier.weight(1.0f),
                    onClick = { /*TODO*/ }
                ) {
                    Text(text = "RECEIVE")
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
                            balance = dashboardUiState.amount,
                            trends = dashboardUiState.trends
                        )
                    }
                }
            },
            listState = lazyListState,
            listContent = { contentModifier ->
                DefaultPagingStateColumn(
                    modifier = contentModifier
                        .clip(RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp))
                        .background(MaterialTheme.colorScheme.surfaceVariant),
                    state = lazyListState,
                    paging = transactionPaging,
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    itemKey = { index -> transactionPaging[index]!!.hash },
                    itemView = { transaction ->
                        TransactionSummaryView(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(end = 16.dp, start = 8.dp),
                            transaction = transaction,
                            itemClicked = {
                                println(it.hash)
                            }
                        )
                    }
                )
            }
        )
    }
}
