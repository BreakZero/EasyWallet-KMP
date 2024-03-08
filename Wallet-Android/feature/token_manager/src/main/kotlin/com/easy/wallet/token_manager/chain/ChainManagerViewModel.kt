package com.easy.wallet.token_manager.chain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easy.wallet.shared.data.repository.asset.ChainRepository
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

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

    fun save() {
        viewModelScope.launch {
            localChainRepository.addOne(
                name = "Bitcoin",
                website = "https://bitcoin.org/en/",
                explorer = "https://www.blockchain.com/explorer",
                rpcUrl = "",
                chainId = null
            )
        }
    }
}
