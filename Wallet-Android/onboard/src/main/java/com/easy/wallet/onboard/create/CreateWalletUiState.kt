package com.easy.wallet.onboard.create

data class CreateWalletUiState(
    val passwordUiState: PasswordUiState = PasswordUiState()
)

data class PasswordUiState(
    val password: String = "",
    val confirmPassword: String = "",
    val isShowPassword: Boolean = false,
    val isTermsOfServiceAgreed: Boolean = false,
    val isTermsOfServiceOpen: Boolean = false,
    val passwordError: String? = null,
    val confirmPasswordError: String? = null
) {
    fun isAvailable(): Boolean {
        return isTermsOfServiceAgreed && password.isNotBlank() && confirmPassword.isNotBlank()
    }
    fun isMatch(): Boolean {
        return password.isNotBlank() && confirmPassword.isNotBlank() && password == confirmPassword
    }
}