package com.easy.wallet.home.transactions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.easy.wallet.design.component.DefaultPagingStateColumn
import com.easy.wallet.home.transactions.component.AmountHeaderView
import com.easy.wallet.home.transactions.component.TransactionView
import com.easy.wallet.model.data.Transaction
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun TransactionsRoute() {
    val viewModel: TransactionsViewModel = koinViewModel()
    val uiState = viewModel.transactionUiState.collectAsLazyPagingItems()
    TransactionsScreen(transactionPaging = uiState)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TransactionsScreen(
    transactionPaging: LazyPagingItems<Transaction>
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Transactions") })
        },
        modifier = Modifier.fillMaxSize()
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            AmountHeaderView(
                modifier = Modifier.fillMaxWidth().height(275.dp)
            )
            DefaultPagingStateColumn(
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
    }
}
