package com.easy.wallet.token_manager.chain.editor

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.easy.wallet.android.core.BaseViewModel
import com.easy.wallet.shared.data.repository.asset.PlatformRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@OptIn(ExperimentalFoundationApi::class)
internal class ChainEditorViewModel(
    platformRepository: PlatformRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<ChainEditorUiEvent>() {

    private val platformId: String = checkNotNull(savedStateHandle["platformId"])

    val chainEditorUiState = platformRepository.findPlatformByIdStream(platformId).map {
        ChainEditorUiState()
    }.catch {
        emit(ChainEditorUiState())
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(3_000), ChainEditorUiState())

    override fun handleEvent(event: ChainEditorUiEvent) {
        when (event) {
            ChainEditorUiEvent.OnSavedClick -> {
                with(chainEditorUiState.value) {
                    // add validate information
                    dispatchEvent(ChainEditorUiEvent.NavigateUp)
                }
            }
            else -> dispatchEvent(ChainEditorUiEvent.NavigateUp)
        }
    }

    private fun TextFieldState.asString() = this.text.toString()
}
