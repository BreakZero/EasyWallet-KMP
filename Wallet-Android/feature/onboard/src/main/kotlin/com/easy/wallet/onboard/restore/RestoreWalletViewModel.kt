package com.easy.wallet.onboard.restore

import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshotFlow
import androidx.lifecycle.viewModelScope
import com.easy.wallet.android.core.BaseViewModel
import com.easy.wallet.shared.data.multiwallet.MultiWalletRepository
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.launch

internal class RestoreWalletViewModel(
    private val multiWalletRepository: MultiWalletRepository
) : BaseViewModel<RestoreWalletEvent>() {
    private val passwordValidationRegex =
        """^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}${'$'}""".toRegex()

    val seedPhraseFieldState = TextFieldState()
    val passwordFieldState = TextFieldState()
    val confirmPasswordFieldState = TextFieldState()

    val isPasswordValid by derivedStateOf {
        passwordFieldState.text.matches(passwordValidationRegex).also {
            println("===== $it")
        }
    }

    suspend fun checkConfirmPassword() {
        snapshotFlow { confirmPasswordFieldState.text }.debounce(300).collectLatest { confirm ->
            if (isPasswordValid) {
                println("=== $confirm")
            }
        }
    }

    override fun handleEvent(event: RestoreWalletEvent) {
        when (event) {
            is RestoreWalletEvent.OnImport -> {
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
