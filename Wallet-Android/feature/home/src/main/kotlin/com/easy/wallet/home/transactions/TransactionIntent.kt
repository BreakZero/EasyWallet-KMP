package com.easy.wallet.home.transactions

import androidx.paging.PagingData
import com.easy.wallet.model.data.Transaction

internal sealed interface TransactionUiState {
    data class Success(val transactions: PagingData<Transaction>): TransactionUiState
}

internal sealed interface TransactionEvent {

}
