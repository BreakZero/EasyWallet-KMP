package com.easy.wallet.onboard.restore

internal data class RestoreWalletUiState(
  val mnemonicError: String? = null,
  val passwordError: Boolean = false,
  val confirmPasswordError: Boolean = false
)
