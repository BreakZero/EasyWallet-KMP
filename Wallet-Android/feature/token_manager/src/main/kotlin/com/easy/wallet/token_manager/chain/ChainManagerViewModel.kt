package com.easy.wallet.token_manager.chain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easy.wallet.shared.data.repository.asset.ChainRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class ChainManagerViewModel(
    private val localChainRepository: ChainRepository
) : ViewModel() {
    init {
        localChainRepository.allChains().onEach {
            it.onEach {
                println(it)
            }
        }.launchIn(viewModelScope)
    }
}
