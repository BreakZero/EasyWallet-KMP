package com.easy.wallet.marketplace

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easy.wallet.network.source.okx.dto.OptionArg
import com.easy.wallet.shared.data.okx.OKXDataRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MarketplaceViewModel(
    private val okxDataRepository: OKXDataRepository
) : ViewModel() {

    private val args = listOf(
        OptionArg(channel = "index-candle15m", instId = "BTC-USD")
    )

    private val _content = MutableStateFlow("Marketplace")
    val content = _content.asStateFlow()

    init {
        viewModelScope.launch {
            okxDataRepository.connect("/ws/v5/business") {
                delay(1000)
                okxDataRepository.subscribe(
                    args = listOf(
                        OptionArg(channel = "index-candle1m", instId = "BTC-USD")
                    )
                ) { marketInfo ->
                    println(marketInfo)
                    _content.update {
                        marketInfo
                    }
                }
            }
        }
    }

    override fun onCleared() {
        viewModelScope.launch {
            okxDataRepository.unsubscribe(args)
        }
        super.onCleared()
    }
}
