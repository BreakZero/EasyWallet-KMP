package com.easy.wallet.onboard.restore

import androidx.lifecycle.ViewModel
import com.easy.wallet.data.multiwallet.MultiWalletRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class RestoreWalletViewModel(
    private val multiWalletRepository: MultiWalletRepository
): ViewModel() {
    private val _restoreWalletUiState = MutableStateFlow(RestoreWalletUiState())
    val restoreWalletUiState = _restoreWalletUiState.asStateFlow()

    fun onEvent(event: RestoreWalletEvent) {

    }
}