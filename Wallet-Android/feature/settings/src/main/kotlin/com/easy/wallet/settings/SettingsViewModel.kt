package com.easy.wallet.settings

import androidx.lifecycle.viewModelScope
import com.easy.wallet.android.core.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

internal class SettingsViewModel : BaseViewModel<SettingsEvent>() {
  private val biometricEnabled = MutableStateFlow(false)

  val settingsUiState = biometricEnabled
    .map {
      SettingsUiState(biometricEnabled = it)
    }.stateIn(
      viewModelScope,
      SharingStarted.WhileSubscribed(3_000),
      SettingsUiState(biometricEnabled = false)
    )

  override fun handleEvent(event: SettingsEvent) {
    when (event) {
      is SettingsEvent.BiometricCheckedChanged -> {
        biometricEnabled.update { event.enabled }
      }

      else -> dispatchEvent(event)
    }
  }
}
