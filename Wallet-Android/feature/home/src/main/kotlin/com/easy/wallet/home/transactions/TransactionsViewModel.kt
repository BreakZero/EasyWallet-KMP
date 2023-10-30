package com.easy.wallet.home.transactions

import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.easy.wallet.android.core.BaseViewModel
import com.easy.wallet.shared.domain.TransactionsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn

internal class TransactionsViewModel(
    transactionsUseCase: TransactionsUseCase
) : BaseViewModel<TransactionsEvent>() {
    private val pageFlow = Pager(
        config = PagingConfig(pageSize = Int.MAX_VALUE, prefetchDistance = 2),
        pagingSourceFactory = {
            TransactionPagingSource("", transactionsUseCase)
        },
    ).flow

    val transactionUiState = pageFlow.distinctUntilChanged()
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), PagingData.empty())
    override fun handleEvent(event: TransactionsEvent) {
    }
}
