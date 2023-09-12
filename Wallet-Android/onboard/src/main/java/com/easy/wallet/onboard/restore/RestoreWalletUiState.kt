package com.easy.wallet.onboard.restore

import com.trustwallet.core.Mnemonic

internal data class RestoreWalletUiState(
    val seedPhraseError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val matchError: String? = null
)

internal data class SeedPhraseForm(
    val seedPhrase: String = "",
    val password: String = "",
    val confirmPassword: String = ""
)

internal fun validateSeedPhrase(
    uiState: SeedPhraseForm
): ValidationResult {
    var result = ValidationResult()
    if (uiState.seedPhrase.isBlank()) {
        result = result.copy(seedPhraseError = "The seed phrase can't be empty.")
    } else if (!Mnemonic.isValid(uiState.seedPhrase)) {
        result = result.copy(seedPhraseError = "The seed phrase is incorrect.")
    }

    if (uiState.password.isBlank()) {
        result = result.copy(passwordError = "The password can't be empty.")
    }
    if (uiState.confirmPassword.isBlank()) {
        result = result.copy(confirmPasswordError = "The confirm password can't be empty.")
    }
    if ((uiState.password.isNotBlank() && uiState.confirmPassword.isNotBlank()) && uiState.password != uiState.confirmPassword) {
        result = result.copy(matchError = "Passwords are not match.")
    }
    return result
}

internal data class ValidationResult(
    val seedPhraseError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val matchError: String? = null
)
