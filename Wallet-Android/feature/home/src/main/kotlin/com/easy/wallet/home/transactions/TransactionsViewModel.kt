package com.easy.wallet.home.transactions

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.easy.wallet.android.core.BaseViewModel
import com.easy.wallet.home.navigation.TokenArgs
import com.easy.wallet.shared.data.repository.SupportedTokenRepository
import com.easy.wallet.shared.domain.TransactionsUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.coroutines.flow.stateIn

internal class TransactionsViewModel(
    savedStateHandle: SavedStateHandle,
    supportedTokenRepository: SupportedTokenRepository,
    transactionsUseCase: TransactionsUseCase
) : BaseViewModel<TransactionEvent>() {
    private val tokenArgs: TokenArgs = TokenArgs(savedStateHandle)
    private val tokenId = tokenArgs.tokenId

    private val _tokenFlow = supportedTokenRepository.findTokenByIdFlow(tokenId)

    private val _transactionPageFlow = _tokenFlow
        .mapNotNull { it }.flatMapConcat {
            transactionsUseCase(it).flow
        }.catch { emit(PagingData.empty()) }

    val tokenInformation = _tokenFlow.stateIn(viewModelScope, SharingStarted.Lazily, null)

    val transactionUiState = _transactionPageFlow.distinctUntilChanged()
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), PagingData.empty())

    override fun handleEvent(event: TransactionEvent) {
    }
}
