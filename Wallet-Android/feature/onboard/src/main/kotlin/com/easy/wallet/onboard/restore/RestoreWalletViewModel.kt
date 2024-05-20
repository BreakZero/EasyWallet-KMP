package com.easy.wallet.onboard.restore

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import com.easy.wallet.android.core.BaseViewModel
import com.easy.wallet.onboard.util.PasswordValidateResult
import com.easy.wallet.onboard.util.PasswordValidation
import com.easy.wallet.shared.data.multiwallet.MultiWalletRepository
import com.trustwallet.core.Mnemonic
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class RestoreWalletViewModel(
    private val multiWalletRepository: MultiWalletRepository
) : BaseViewModel<RestoreWalletEvent>() {

    val seedPhraseFieldState = TextFieldState()
    val passwordFieldState = TextFieldState()
    val confirmPasswordFieldState = TextFieldState()

    private val _uiState: MutableStateFlow<RestoreWalletUiState> = MutableStateFlow(RestoreWalletUiState())
    val uiState = _uiState.asStateFlow()

    suspend fun mnemonicValidate() {
        snapshotFlow { seedPhraseFieldState.text }.debounce(300).collectLatest {
            val isValid = Mnemonic.isValid(it.toString())
            _uiState.update { it.copy(mnemonicError = if (isValid) null else "Mnemonic is invalid") }
        }
    }

    override fun handleEvent(event: RestoreWalletEvent) {
        when (event) {
            is RestoreWalletEvent.OnImport -> {
                _uiState.update { it.copy(passwordError = false, confirmPasswordError = false) }
                val result = PasswordValidation.validate(
                    password = passwordFieldState.text.toString(),
                    confirmPassword = confirmPasswordFieldState.text.toString()
                )
                when(result) {
                    PasswordValidateResult.PasswordNotMatch -> {
                        _uiState.update { it.copy(passwordError = true) }
                    }
                    PasswordValidateResult.ConfirmPasswordNotMatch -> {
                        _uiState.update { it.copy(confirmPasswordError = true) }
                    }
                    PasswordValidateResult.Pass -> {
                        viewModelScope.launch {
                            multiWalletRepository.insertOne(
                                mnemonic = seedPhraseFieldState.text.toString(),
                                passphrase = "",
                            ) {
                                dispatchEvent(event)
                            }
                        }
                    }
                }
            }
        }
    }
}
