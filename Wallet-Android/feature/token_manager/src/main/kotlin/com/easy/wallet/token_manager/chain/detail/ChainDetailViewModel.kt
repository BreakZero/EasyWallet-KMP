package com.easy.wallet.token_manager.chain.detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.easy.wallet.shared.data.repository.asset.ChainManageRepository

class ChainDetailViewModel(
    private val localChainRepository: ChainManageRepository,
    savedStateHandle: SavedStateHandle
): ViewModel() {
    private val chainId: Long = checkNotNull(savedStateHandle["chainId"])

//    val chainEditorUiState = localChainRepository.getChainById(chainId).map {
//        ChainEditorUiState(
//            name = TextFieldState(it.name),
//            website = TextFieldState(it.website),
//            explorer = TextFieldState(it.explorer.orEmpty()),
//            rpcUrl = TextFieldState(it.rpcUrl),
//            chainId = TextFieldState(it.chainId.orEmpty())
//        )
//    }.catch {
//        emit(ChainEditorUiState())
//    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(3_000), ChainEditorUiState())
}
