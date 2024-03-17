package com.easy.wallet.token_manager.chain.manager

import androidx.lifecycle.viewModelScope
import com.easy.wallet.android.core.BaseViewModel
import com.easy.wallet.shared.data.repository.asset.ChainManageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class ChainManagerViewModel(
    private val localChainRepository: ChainManageRepository
) : BaseViewModel<ChainManagerEvent>() {

    private val isNeedFetch: MutableStateFlow<Boolean> = MutableStateFlow(true)

    val chainManagerUiState: StateFlow<ChainUiState> =
        isNeedFetch.flatMapConcat { localChainRepository.allChains() }
            .map(ChainUiState::Success)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(3_000), ChainUiState.Loading)

    override fun handleEvent(event: ChainManagerEvent) {
        when (event) {
            is ChainManagerEvent.ClickAdd -> dispatchEvent(event)
            is ChainManagerEvent.ClickEdit -> dispatchEvent(event)
            is ChainManagerEvent.ClickDeleted -> {
                onDelete(event.id)
            }
        }
    }

    private fun onDelete(id: Long) {
        viewModelScope.launch {
            localChainRepository.deleteById(id)
            isNeedFetch.update { !it }
        }
    }
}
