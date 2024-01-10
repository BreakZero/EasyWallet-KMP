package com.easy.wallet.home.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.easy.wallet.design.component.DefaultPagingStateColumn
import com.easy.wallet.home.component.CollapsingToolbarWithLazyList
import com.easy.wallet.home.component.DashboardView
import com.easy.wallet.home.transactions.component.TransactionView
import com.easy.wallet.model.data.Transaction
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun TransactionsRoute() {
    val viewModel: TransactionsViewModel = koinViewModel()
    val transactionUiState = viewModel.transactionUiState.collectAsLazyPagingItems()
    TransactionsScreen(transactionPaging = transactionUiState)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TransactionsScreen(
    transactionPaging: LazyPagingItems<Transaction>
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Transactions") }, navigationIcon = {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(imageVector = Icons.Default.ArrowBack, contentDescription = null)
                }
            })
        },
        modifier = Modifier.fillMaxSize()
    ) {
        val lazyListState = rememberLazyListState()
        CollapsingToolbarWithLazyList(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            header = { headerModifier ->
                DashboardView(
                    modifier = headerModifier
                        .height(260.dp)
                )
            },
            listState = lazyListState,
            listContent = { contentModifier ->
                DefaultPagingStateColumn(
                    modifier = contentModifier
                        .clip(RoundedCornerShape(topEnd = 20.dp, topStart = 20.dp))
                        .background(MaterialTheme.colorScheme.secondaryContainer),
                    state = lazyListState,
                    paging = transactionPaging,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) { transaction ->
                    TransactionView(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(end = 16.dp, start = 8.dp),
                        transaction = transaction,
                    )
                }
            }
        )
    }
}
