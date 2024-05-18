package com.easy.wallet.home.transactions

import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.easy.wallet.android.core.BaseViewModel
import com.easy.wallet.shared.domain.CoinBalanceUseCase
import com.easy.wallet.shared.domain.CoinTrendUseCase
import com.easy.wallet.shared.domain.TransactionPagerUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn

internal class TransactionsViewModel(
    private val coinId: String,
    getBalanceUseCase: CoinBalanceUseCase,
    coinTrendUseCase: CoinTrendUseCase,
    tnxPagerUseCase: TransactionPagerUseCase
) : BaseViewModel<TransactionEvent>() {

    init {
        println("==== $coinId")
    }

    val dashboardUiState = combine(
        getBalanceUseCase(coinId),
        coinTrendUseCase(coinId)
    ) { assetBalance, trends ->
        TransactionDashboardUiState.Success(
            assetBalance,
            trends
        ) as TransactionDashboardUiState
    }.catch {
        it.printStackTrace()
        emit(TransactionDashboardUiState.Error)
    }.stateIn(viewModelScope, SharingStarted.Lazily, TransactionDashboardUiState.Loading)

    val transactionPager = tnxPagerUseCase(coinId).distinctUntilChanged()
        .catch { emit(PagingData.empty()) }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), PagingData.empty())

    override fun handleEvent(event: TransactionEvent) {
        when (event) {
            is TransactionEvent.ClickSend -> dispatchEvent(TransactionEvent.ClickSend(coinId))
            else -> dispatchEvent(event)
        }
    }
}
