package com.easy.wallet.onboard.util

internal object PasswordValidation {
  private val passwordRegex = """^(?=.*[A-Za-z])(?=.*\d)[A-Za-z\d]{8,}${'$'}""".toRegex()

  fun validate(password: String, confirmPassword: String): PasswordValidateResult {
    if (password.isBlank() || !password.matches(passwordRegex)) {
      return PasswordValidateResult.PasswordNotMatch
    }
    if (confirmPassword != password) {
      return PasswordValidateResult.ConfirmPasswordNotMatch
    }
    return PasswordValidateResult.Pass
  }
}

internal enum class PasswordValidateResult {
  PasswordNotMatch,
  ConfirmPasswordNotMatch,
  Pass
}
