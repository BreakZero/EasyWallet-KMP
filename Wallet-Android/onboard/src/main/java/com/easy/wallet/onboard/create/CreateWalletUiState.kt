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
    val error: String? = null
)
