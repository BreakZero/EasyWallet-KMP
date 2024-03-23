package com.easy.wallet.home.transactions

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.easy.wallet.android.core.BaseViewModel
import com.easy.wallet.home.navigation.TokenArgs
import com.easy.wallet.model.data.Transaction
import com.easy.wallet.shared.data.multiwallet.MultiWalletRepository
import com.easy.wallet.shared.data.repository.SupportedTokenRepository
import com.easy.wallet.shared.domain.CoinTrendUseCase
import com.easy.wallet.shared.domain.TokenAmountUseCase
import com.easy.wallet.shared.domain.TransactionsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.stateIn

internal class TransactionsViewModel(
    savedStateHandle: SavedStateHandle,
    multiWalletRepository: MultiWalletRepository,
    supportedTokenRepository: SupportedTokenRepository,
    tokenAmountUseCase: TokenAmountUseCase,
    coinTrendUseCase: CoinTrendUseCase,
    transactionsUseCase: TransactionsUseCase
) : BaseViewModel<TransactionEvent>() {
    private val tokenArgs: TokenArgs = TokenArgs(savedStateHandle)
    private val tokenId = tokenArgs.tokenId

    private val _tokenFlow = supportedTokenRepository.findTokenByIdFlow(tokenId)

    val dashboardUiState = combine(
        multiWalletRepository.forActivatedOne().filterNotNull(),
        _tokenFlow.filterNotNull()
    ) { wallet, tokenInformation ->
        combine(
            tokenAmountUseCase(wallet, tokenInformation),
            coinTrendUseCase(wallet, tokenInformation)
        ) { amount, trends ->
            TransactionDashboardUiState.Success(tokenInformation, amount, trends)
        }.first()
    }.stateIn(viewModelScope, SharingStarted.Lazily, TransactionDashboardUiState.Loading)

    val transactionPager = _tokenFlow.filterNotNull().flatMapConcat {
        transactionsUseCase(it).flow
    }.distinctUntilChanged()
        .catch { PagingData.empty<Transaction>() }
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), PagingData.empty())

    override fun handleEvent(event: TransactionEvent) {
    }
}
