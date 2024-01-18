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
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.stateIn

internal class TransactionsViewModel(
    savedStateHandle: SavedStateHandle,
    supportedTokenRepository: SupportedTokenRepository,
    transactionsUseCase: TransactionsUseCase
) : BaseViewModel<TransactionEvent>() {
    private val tokenArgs: TokenArgs = TokenArgs(savedStateHandle)
    private val tokenId = tokenArgs.tokenId

    private val _transactionPageFlow = supportedTokenRepository.findTokenByIdFlow(tokenId).flatMapConcat {
        transactionsUseCase(it, "0x81080a7e991bcDdDBA8C2302A70f45d6Bd369Ab5").flow
    }

    val transactionUiState = _transactionPageFlow.distinctUntilChanged()
        .cachedIn(viewModelScope)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), PagingData.empty())

    override fun handleEvent(event: TransactionEvent) {
    }
}
