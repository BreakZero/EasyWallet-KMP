package com.easy.wallet.token_manager.token.editor

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.easy.wallet.android.core.BaseViewModel
import com.easy.wallet.shared.data.repository.asset.ChainManageRepository
import com.easy.wallet.shared.data.repository.asset.TokenManageRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.util.UUID

@OptIn(ExperimentalFoundationApi::class)
internal class TokenEditorViewModel(
    private val chainManageRepository: ChainManageRepository,
    private val tokenManageRepository: TokenManageRepository,
    savedStateHandle: SavedStateHandle
) : BaseViewModel<TokenEditorEvent>() {

    private val tokenId: String? = savedStateHandle["tokenId"]

    private val _localChains = chainManageRepository.allChains()
    private val _chainId: MutableStateFlow<Long> = MutableStateFlow(-1L)
    private val _isActive: MutableStateFlow<Boolean> = MutableStateFlow(false)

    val tokenEditorUiState = combine(
        _localChains,
        _chainId,
        _isActive
    ) { localChains, chainId, isActive ->
        val chainName = localChains.find { it.id == chainId }?.name.orEmpty()
        TokenEditorUiState(
            localChains = localChains,
            chainName = chainName,
            isActive = isActive
        )
    }.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(3_000),
        TokenEditorUiState(
            localChains = emptyList(),
            chainName = ""
        )
    )

    fun onChainIdChanged(chainId: Long) {
        _chainId.update { chainId }
    }

    override fun handleEvent(event: TokenEditorEvent) {
        when (event) {
            is TokenEditorEvent.ClickSaved -> {
                // onSaved()
            }
        }
    }

    private fun onSaved() {
        log()
        /*viewModelScope.launch {
            tokenManageRepository.addOne(
                id = UUID.randomUUID().toString(),
                chainId = 6L,
                name = "Compound",
                symbol = "COMP",
                decimal = 18,
                contractAddress = "0xc00e94Cb662C3520282E6f5717214004A7f26888",
                iconUri = "https://assets-cdn.trustwallet.com/blockchains/ethereum/assets/0xc00e94Cb662C3520282E6f5717214004A7f26888/logo.png",
                isActive = _isActive.value
            )
        }*/
    }

    private fun log() {
        val info = buildString {
            appendLine(_chainId.value)
            appendLine(_isActive.value)
        }
    }
}
