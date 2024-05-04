package com.easy.wallet.token_manager.token.editor

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.lifecycle.viewModelScope
import com.easy.wallet.android.core.BaseViewModel
import com.easy.wallet.shared.data.repository.asset.CoinRepository
import com.easy.wallet.shared.data.repository.asset.PlatformRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
internal class TokenEditorViewModel(
    coinRepository: CoinRepository,
    platformRepository: PlatformRepository
) : BaseViewModel<TokenEditorEvent>() {

    private val _allPlatforms = platformRepository.allPlatformStream()
    private val _selectedPlatform: MutableStateFlow<String> = MutableStateFlow("")
    private val _isActive: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val tokenEditorFields = TokenEditorFields()

    val tokenEditorUiState = combine(
        _allPlatforms,
        _selectedPlatform,
        _isActive
    ) { allPlatforms, selectedPlatform, isActive ->
        val evmPlatforms = allPlatforms.filter { it.network != null }
        val platformName = allPlatforms.find { it.id == selectedPlatform }?.shortName.orEmpty()
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
                onSaved()
            }

            is TokenEditorEvent.OnActiveChanged -> {
                _isActive.update { event.isActive }
            }

            is TokenEditorEvent.OnChainChanged -> {
                _selectedPlatform.update { event.platformId }
            }
        }
    }

    private fun onSaved() {
        log()
        viewModelScope.launch {
            with(tokenEditorFields) {
                log()
                // insert evm coin
                /*tokenManageRepository.addOne(
                    id = UUID.randomUUID().toString(),
                    chainId = _chainId.value,
                    name = name.contentValue(),
                    symbol = symbol.contentValue(),
                    decimal = decimals.contentValue().toIntOrNull() ?: 18,
                    contractAddress = contract.contentValue().ifBlank { null },
                    iconUri = iconUri.contentValue(),
                    isActive = _isActive.value,
                    tags = ""
                )*/
            }
        }
    }

    private fun TextFieldState.contentValue() = this.text.toString()

    private fun log() {
        val info = buildString {
            appendLine("=========================")
            appendLine(_selectedPlatform.value)
            appendLine(_isActive.value)
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
