package com.easy.wallet.token_manager.token.manager

import androidx.lifecycle.viewModelScope
import com.easy.wallet.android.core.BaseViewModel
import com.easy.wallet.shared.data.repository.asset.TokenManageRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class TokenManagerViewModel(
    private val tokenManageRepository: TokenManageRepository
) : BaseViewModel<TokenManagerEvent>() {
    private val isNeedFetch: MutableStateFlow<Boolean> = MutableStateFlow(true)

    val tokenManagerUiState = isNeedFetch.flatMapConcat { tokenManageRepository.allTokens() }.map {
        TokenManagerUiState.Success(tokens = it)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(3_000), TokenManagerUiState.Loading)

    override fun handleEvent(event: TokenManagerEvent) {
        when (event) {
            is TokenManagerEvent.ClickDelete -> {
                onDeleted(event.ids)
            }

            is TokenManagerEvent.ClickInEditModel -> {}
            TokenManagerEvent.ClickAdd, is TokenManagerEvent.ClickEdit, TokenManagerEvent.ClickPopBack -> {
                dispatchEvent(event)
            }
        }
    }

    private fun onDeleted(ids: List<String>) {
        viewModelScope.launch {
            tokenManageRepository.deleteByIds(ids)
        }
    }
}
