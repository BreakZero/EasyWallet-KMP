package com.easy.wallet.onboard.restore

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.viewModelScope
import com.easy.wallet.android.core.BaseViewModel
import com.easy.wallet.shared.data.multiwallet.MultiWalletRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class RestoreWalletViewModel(
    private val multiWalletRepository: MultiWalletRepository
) : BaseViewModel<RestoreWalletEvent>() {
    private val _restoreWalletUiState = MutableStateFlow(RestoreWalletUiState())
    internal val restoreWalletUiState = _restoreWalletUiState.asStateFlow()

    internal var seedPhraseForm: SeedPhraseForm by mutableStateOf(SeedPhraseForm())
        private set

    override fun handleEvent(event: RestoreWalletEvent) {
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
                    result.matchError,
                )
                if (errors.isEmpty()) {
                    viewModelScope.launch {
                        multiWalletRepository.insertOne(
                            mnemonic = seedPhraseForm.seedPhrase,
                            passphrase = "",
                        ) {
                            dispatchEvent(event)
                        }
                    }
                } else {
                    _restoreWalletUiState.update {
                        it.copy(
                            seedPhraseError = result.seedPhraseError,
                            passwordError = result.passwordError,
                            confirmPasswordError = result.confirmPasswordError,
                            matchError = result.matchError,
                        )
                    }
                }
            }
        }
    }
}
