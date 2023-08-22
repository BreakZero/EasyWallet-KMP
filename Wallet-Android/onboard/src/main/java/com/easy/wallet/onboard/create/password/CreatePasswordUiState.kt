package com.easy.wallet.onboard.create.password

data class CreatePasswordUiState(
    val password: String = "",
    val confirmPassword: String = "",
    val isShowPassword: Boolean = false,
    val isTermsOfServiceAgreed: Boolean = false,
    val isTermsOfServiceOpen: Boolean = false,
    val error: String? = null
)
