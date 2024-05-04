package com.easy.wallet.token_manager.chain.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easy.wallet.shared.data.repository.asset.PlatformRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ChainDetailViewModel(
    platformRepository: PlatformRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val chainId: String = checkNotNull(savedStateHandle["platformId"])

    internal val chainInfoUiState = platformRepository.findPlatformByIdStream(chainId).map {
        it?.let {
            ChainDetailUiState.Success(it)
        } ?: ChainDetailUiState.Error("Chain $chainId could not found")
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(3_000), ChainDetailUiState.Loading)
}
