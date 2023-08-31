package com.easy.wallet.onboard.restore

data class RestoreWalletUiState(
    val seed: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val error: String? = null
)
