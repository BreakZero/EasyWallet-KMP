package com.easy.wallet.onboard.restore

import com.trustwallet.core.Mnemonic

internal data class RestoreWalletUiState(
    val seedPhraseError: String? = null,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null,
    val matchError: String? = null
)

internal fun validate(
    seedPhrase: String,
    password: String,
    confirmPassword: String
): ValidationResult {
    var result = ValidationResult()
    if (seedPhrase.isBlank()) {
        result = result.copy(seedPhraseError = "The seed phrase can't be empty.")
    } else if (!Mnemonic.isValid(seedPhrase)) {
        result = result.copy(seedPhraseError = "The seed phrase is incorrect.")
    }

    if (password.isBlank()) {
        result = result.copy(passwordError = "The password can't be empty.")
    }
    if (confirmPassword.isBlank()) {
        result = result.copy(confirmPasswordError = "The confirm password can't be empty.")
    }
    if ((password.isNotBlank() && confirmPassword.isNotBlank()) && password != confirmPassword) {
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
