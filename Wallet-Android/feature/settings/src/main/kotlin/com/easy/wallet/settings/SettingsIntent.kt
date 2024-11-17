package com.easy.wallet.settings

internal data class SettingsUiState(
  val biometricEnabled: Boolean = false
)

internal sealed interface SettingsEvent {
  data object PopBack : SettingsEvent

  data object ClickViewSupportedChains : SettingsEvent

  data object ClickTokenManager : SettingsEvent

  data class BiometricCheckedChanged(
    val enabled: Boolean
  ) : SettingsEvent
}
