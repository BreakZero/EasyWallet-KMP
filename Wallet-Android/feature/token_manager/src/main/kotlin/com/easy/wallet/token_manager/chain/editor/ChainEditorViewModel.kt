package com.easy.wallet.token_manager.chain.editor

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.easy.wallet.android.core.BaseViewModel
import com.easy.wallet.shared.data.repository.asset.ChainRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
internal class ChainEditorViewModel(
    private val localChainRepository: ChainRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ChainEditorUiEvent>() {

    private val chainId: Long = checkNotNull(savedStateHandle["chainId"])

    val chainEditorUiState = localChainRepository.getChainById(chainId).map {
        ChainEditorUiState(
            name = TextFieldState(it.name),
            website = TextFieldState(it.website),
            explorer = TextFieldState(it.explorer.orEmpty()),
            rpcUrl = TextFieldState(it.rpcUrl),
            chainId = TextFieldState(it.chainId.orEmpty())
        )
    }.catch {
        emit(ChainEditorUiState())
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(3_000), ChainEditorUiState())

    override fun handleEvent(event: ChainEditorUiEvent) {
        when (event) {
            ChainEditorUiEvent.OnSavedClick -> {
                with(chainEditorUiState.value) {
                    // add validate information
                    viewModelScope.launch {
                        localChainRepository.addOne(
                            name.asString(),
                            website.asString(),
                            explorer.asString(),
                            rpcUrl.asString(),
                            chainId.asString()
                        )
                    }
                    dispatchEvent(ChainEditorUiEvent.NavigateUp)
                }
            }
            else -> dispatchEvent(ChainEditorUiEvent.NavigateUp)
        }
    }

    private fun TextFieldState.asString() = this.text.toString()
}
