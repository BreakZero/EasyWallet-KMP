package com.easy.wallet.assetmanager.platform.manager

import androidx.lifecycle.viewModelScope
import com.easy.wallet.android.core.BaseViewModel
import com.easy.wallet.shared.data.repository.asset.PlatformRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

internal class ChainManagerViewModel(
    platformRepository: PlatformRepository
) : BaseViewModel<ChainManagerEvent>() {

    val chainManagerUiState: StateFlow<ChainUiState> = platformRepository.allPlatformStream()
        .map(ChainUiState::Success)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(3_000), ChainUiState.Loading)

    override fun handleEvent(event: ChainManagerEvent) {
        when (event) {
            is ChainManagerEvent.ClickAdd -> dispatchEvent(event)
            is ChainManagerEvent.ClickEdit -> dispatchEvent(event)
            is ChainManagerEvent.ClickDeleted -> {
                onDelete(event.id)
            }

            ChainManagerEvent.InitialDefaultData -> initialDefaultChains()
        }
    }

    private fun onDelete(id: String) {
        // TODO
    }

    private fun initialDefaultChains() {
        // only support add evm chain
    }
}
