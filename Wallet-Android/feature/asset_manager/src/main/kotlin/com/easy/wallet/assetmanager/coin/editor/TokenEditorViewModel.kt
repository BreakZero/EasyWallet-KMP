package com.easy.wallet.assetmanager.coin.editor

import androidx.compose.foundation.text.input.TextFieldState
import androidx.lifecycle.viewModelScope
import com.easy.wallet.android.core.BaseViewModel
import com.easy.wallet.model.asset.AssetPlatform
import com.easy.wallet.shared.data.repository.asset.CoinRepository
import com.easy.wallet.shared.data.repository.asset.PlatformRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

internal class TokenEditorViewModel(
  private val coinRepository: CoinRepository,
  private val platformRepository: PlatformRepository
) : BaseViewModel<TokenEditorEvent>() {
  private val allPlatforms = platformRepository.allPlatformStream()
  private val selectedPlatform: MutableStateFlow<AssetPlatform?> = MutableStateFlow(null)
  private val isActive: MutableStateFlow<Boolean> = MutableStateFlow(false)

  val tokenEditorFields = TokenEditorFields()

  val tokenEditorUiState = combine(
    allPlatforms,
    selectedPlatform,
    isActive
  ) { allPlatforms, selectedPlatform, isActive ->
    val evmPlatforms = allPlatforms.filter { it.network != null }
    val platformName = selectedPlatform?.shortName.orEmpty()
    TokenEditorUiState(
      localPlatforms = evmPlatforms,
      selectedPlatformName = platformName,
      isActive = isActive
    )
  }.stateIn(
    viewModelScope,
    SharingStarted.WhileSubscribed(3_000),
    TokenEditorUiState(
      localPlatforms = emptyList(),
      selectedPlatformName = ""
    )
  )

  override fun handleEvent(event: TokenEditorEvent) {
    when (event) {
      is TokenEditorEvent.ClickSaved -> {
        onSaved {
          if (it == null) {
            dispatchEvent(TokenEditorEvent.OnSavedSuccess)
          } else {
            // TODO Error handling
          }
        }
      }

      is TokenEditorEvent.OnActiveChanged -> {
        isActive.update { event.isActive }
      }

      is TokenEditorEvent.OnChainChanged -> {
        platformRepository
          .findPlatformByIdStream(event.platformId)
          .onEach { platform ->
            selectedPlatform.update { platform }
          }.launchIn(viewModelScope)
      }

      else -> Unit
    }
  }

  private fun onSaved(onResult: (Throwable?) -> Unit) {
    viewModelScope.launch {
      with(tokenEditorFields) {
        selectedPlatform.value?.let { selectedPlatform ->
          log()
          val requiredFields = validate()
          if (requiredFields.isBlank()) {
            coinRepository.insertCoin(
              id = UUID.randomUUID().toString(),
              platformId = selectedPlatform.id,
              symbol = symbol.contentValue(),
              name = name.contentValue(),
              logoUri = iconUri.contentValue(),
              contract = contract.contentValue(),
              decimalPlace = decimals.contentValue().toIntOrNull() ?: 18
            )
            onResult(null)
          } else {
            onResult(IllegalArgumentException("Required fields ($requiredFields) is empty"))
          }
        } ?: run {
          onResult(IllegalArgumentException("platform not selected..."))
        }
      }
    }
  }

  private fun TextFieldState.contentValue() = this.text.toString()

  private fun TokenEditorFields.validate(): String {
    val requiredFieldEmpty: String = buildString {
      if (name.contentValue().isBlank()) append("name,")
      if (symbol.contentValue().isBlank()) append("symbol,")
      if (decimals.contentValue().isBlank()) append("decimals,")
      if (iconUri.contentValue().isBlank()) append("logoUrl,")
    }

    return requiredFieldEmpty
  }

  private fun log() {
    val info = buildString {
      appendLine("=========================")
      appendLine(selectedPlatform.value)
      appendLine(isActive.value)
      with(tokenEditorFields) {
        appendLine("name: ${name.text}")
        appendLine("symbol: ${symbol.text}")
        appendLine("decimals: ${decimals.text}")
        appendLine("contract: ${contract.text}")
        appendLine("icon uri: ${iconUri.text}")
      }
      appendLine("isActive: ${tokenEditorUiState.value.isActive}")
      appendLine("chain Name: ${tokenEditorUiState.value.selectedPlatformName}")
      appendLine("=========================")
    }
    println(info)
  }
}
