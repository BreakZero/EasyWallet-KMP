package com.easy.wallet.home.transactions

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.easy.wallet.android.core.BaseViewModel
import com.easy.wallet.home.navigation.TokenArgs
import com.easy.wallet.shared.domain.CoinTrendUseCase
import com.easy.wallet.shared.domain.TokenAmountUseCase
import com.easy.wallet.shared.domain.TransactionPagerUseCase
import com.easy.wallet.shared.model.transaction.TransactionUiModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn

internal class TransactionsViewModel(
    savedStateHandle: SavedStateHandle,
    tokenAmountUseCase: TokenAmountUseCase,
    coinTrendUseCase: CoinTrendUseCase,
    tnxPagerUseCase: TransactionPagerUseCase
) : BaseViewModel<TransactionEvent>() {
    private val tokenArgs: TokenArgs = TokenArgs(savedStateHandle)
    private val tokenId = tokenArgs.tokenId

    val dashboardUiState = combine(
        tokenAmountUseCase(tokenId),
        coinTrendUseCase(tokenId)
    ) { amount, trends ->
        TransactionDashboardUiState.Success(amount, trends) as TransactionDashboardUiState
    }.catch {
        emit(TransactionDashboardUiState.Error)
    }.stateIn(viewModelScope, SharingStarted.Lazily, TransactionDashboardUiState.Loading)

    val transactionPager = tnxPagerUseCase(tokenId).distinctUntilChanged()
        .catch { emit(PagingData.empty()) }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), PagingData.empty())

    override fun handleEvent(event: TransactionEvent) {
        when(event) {
            is TransactionEvent.ClickReceive -> dispatchEvent(event)
            is TransactionEvent.ClickSend -> dispatchEvent(event)
        }
    }
}
