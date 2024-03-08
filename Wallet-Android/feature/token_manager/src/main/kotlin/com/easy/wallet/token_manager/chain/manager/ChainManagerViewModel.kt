package com.easy.wallet.token_manager.chain.manager

import androidx.lifecycle.viewModelScope
import com.easy.wallet.android.core.BaseViewModel
import com.easy.wallet.shared.data.repository.asset.ChainRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

internal class ChainManagerViewModel(
    private val localChainRepository: ChainRepository
) : BaseViewModel<ChainManagerEvent>() {

    val chainManagerUiState: StateFlow<ChainUiState> =
        localChainRepository.allChains().map(ChainUiState::Success)
            .stateIn(viewModelScope, SharingStarted.WhileSubscribed(3_000), ChainUiState.Loading)

    override fun handleEvent(event: ChainManagerEvent) {
        when (event) {
            is ChainManagerEvent.ClickEdit -> dispatchEvent(event)
            is ChainManagerEvent.ClickDeleted -> { onDelete(event.id) }
        }
    }

    private fun onDelete(id: Long) {
        viewModelScope.launch {
            localChainRepository.deleteById(id)
        }
    }
}
