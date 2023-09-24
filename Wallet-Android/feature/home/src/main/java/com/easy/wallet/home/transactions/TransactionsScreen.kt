package com.easy.wallet.home.transactions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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

@Composable
internal fun TransactionsScreen(
    transactionPaging: LazyPagingItems<Transaction>
) {
    Column(modifier = Modifier.fillMaxSize()) {
        AmountHeaderView()
        DefaultPagingStateColumn(
            paging = transactionPaging,
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
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
