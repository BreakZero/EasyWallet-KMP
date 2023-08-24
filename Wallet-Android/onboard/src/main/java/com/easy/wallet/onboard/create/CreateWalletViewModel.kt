package com.easy.wallet.onboard.create

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.consumeAsFlow
import kotlinx.coroutines.launch

class CreateWalletViewModel: ViewModel() {

    private val _uiState = MutableStateFlow(CreateWalletUiState())
    val uiState = _uiState.asStateFlow()

    private val _uiEventChannel = Channel<CreateWalletUiEvent>()
    val uiEvent = _uiEventChannel.consumeAsFlow()
    private fun dispatchUiEvent(uiEvent: CreateWalletUiEvent) {
        viewModelScope.launch {
            _uiEventChannel.send(uiEvent)
        }
    }
}