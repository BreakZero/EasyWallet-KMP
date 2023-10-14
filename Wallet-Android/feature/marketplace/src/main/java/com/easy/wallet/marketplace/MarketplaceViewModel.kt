package com.easy.wallet.marketplace

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easy.wallet.shared.data.okx.OKXDataRepository
import kotlinx.coroutines.launch

class MarketplaceViewModel(
    private val okxDataRepository: OKXDataRepository
) : ViewModel() {
    init {
        viewModelScope.launch {
            okxDataRepository.connect("/ws/v5/business"){
                println("on receive: $it")
            }
        }
    }
}
