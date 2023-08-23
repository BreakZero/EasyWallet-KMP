package com.easy.wallet.onboard.create.password

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class CreatePasswordViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(CreatePasswordUiState())
    val uiState = _uiState.asStateFlow()

    internal fun onEvent(event: CreatePasswordEvent) {

    }

    private fun dispatchUiEvent(uiEvent: CreatePasswordUiEvent) {

    }
}