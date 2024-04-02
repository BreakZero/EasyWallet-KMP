package com.easy.wallet.token_manager.chain.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easy.wallet.shared.data.repository.asset.ChainManageRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

class ChainDetailViewModel(
    private val localChainRepository: ChainManageRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {
    private val chainId: Long = checkNotNull(savedStateHandle["chainId"])

    internal val chainInfoUiState = localChainRepository.getChainById(chainId).map {
        ChainDetailUiState.Success(it) as ChainDetailUiState
    }.catch {
        emit(ChainDetailUiState.Error("Chain $chainId could not found"))
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(3_000), ChainDetailUiState.Loading)
}
