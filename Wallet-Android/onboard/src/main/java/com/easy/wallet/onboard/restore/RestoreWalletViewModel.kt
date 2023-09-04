package com.easy.wallet.onboard.restore

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easy.wallet.data.multiwallet.MultiWalletRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class RestoreWalletViewModel(
    private val multiWalletRepository: MultiWalletRepository
) : ViewModel() {
    private val _restoreWalletUiState = MutableStateFlow(RestoreWalletUiState())
    internal val restoreWalletUiState = _restoreWalletUiState.asStateFlow()

    internal var seedPhraseForm: SeedPhraseForm by mutableStateOf(SeedPhraseForm())
        private set

    private val _eventChannel = Channel<RestoreWalletUiEvent>()
    val eventChannel = _eventChannel.receiveAsFlow()

    private fun dispatchUiEvent(uiEvent: RestoreWalletUiEvent) {
        viewModelScope.launch {
            _eventChannel.send(uiEvent)
        }
    }

    fun onEvent(event: RestoreWalletEvent) {
        when (event) {
            is RestoreWalletEvent.SeedChanged -> {
                seedPhraseForm = seedPhraseForm.copy(seedPhrase = event.seed)
            }

            is RestoreWalletEvent.PasswordChanged -> {
                seedPhraseForm = seedPhraseForm.copy(password = event.password)
            }

            is RestoreWalletEvent.ConfirmPasswordChanged -> {
                seedPhraseForm = seedPhraseForm.copy(confirmPassword = event.confirmPassword)
            }

            is RestoreWalletEvent.OnImport -> {
                val result = validateSeedPhrase(seedPhraseForm)
                val errors = listOfNotNull(
                    result.seedPhraseError,
                    result.passwordError,
                    result.confirmPasswordError,
                    result.matchError
                )
                if (errors.isEmpty()) {
                    viewModelScope.launch {
                        multiWalletRepository.insertOne(
                            mnemonic = seedPhraseForm.seedPhrase,
                            passphrase = ""
                        ) {
                            dispatchUiEvent(RestoreWalletUiEvent.ImportSuccess)
                        }
                    }
                } else {
                    _restoreWalletUiState.update {
                        it.copy(
                            seedPhraseError = result.seedPhraseError,
                            passwordError = result.passwordError,
                            confirmPasswordError = result.confirmPasswordError,
                            matchError = result.matchError
                        )
                    }
                }
            }
        }
    }
}