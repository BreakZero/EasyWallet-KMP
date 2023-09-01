package com.easy.wallet.marketplace

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easy.wallet.data.nft.model.OpenSeaNftDto
import com.easy.wallet.data.nft.opensea.OpenseaNftRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MarketplaceViewModel(
    private val openseaNFTRepository: OpenseaNftRepository
): ViewModel() {
    private val _uiState = MutableStateFlow(emptyList<OpenSeaNftDto>())
    val uiState = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            val response = openseaNFTRepository.retrieveNFTsByAccount(
                "0x81080a7e991bcDdDBA8C2302A70f45d6Bd369Ab5",
                "ethereum",
                50
            )
            _uiState.update {
                response.nfts
            }
        }
    }
}