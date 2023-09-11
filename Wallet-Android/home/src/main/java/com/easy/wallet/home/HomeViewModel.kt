package com.easy.wallet.home

import androidx.lifecycle.viewModelScope
import com.easy.wallet.android.core.BaseViewModel
import com.easy.wallet.core.result.Result
import com.easy.wallet.data.hdwallet.HDWalletInMemory
import com.easy.wallet.data.multiwallet.MultiWalletRepository
import com.easy.wallet.data.token.TokenRepository
import com.easy.wallet.home.component.ActionSheetMenu
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

internal class HomeViewModel(
    multiWalletRepository: MultiWalletRepository,
    private val tokenRepository: TokenRepository,
    private val hdWalletInMemory: HDWalletInMemory
) : BaseViewModel<HomeEvent>() {
    init {
        viewModelScope.launch {
            tokenRepository.loadTokens()
        }
    }

    private val _guestUiState = MutableStateFlow(
        GuestUiState()
    )
    internal val guestUiState = _guestUiState.asStateFlow()

    internal val walletUiState = tokenRepository.tokenStream().map {
        WalletUiState(it)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(3_000), WalletUiState())

    internal val hasSetup = multiWalletRepository.forActivatedOne().map {
        it?.let {
            hdWalletInMemory.loadToMemory(it.mnemonic, it.passphrase)
            Result.Success(true)
        } ?: Result.Success(false)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(3000), Result.Loading)

    override fun handleEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.ShowCreateWalletSheet -> {
                _guestUiState.update {
                    it.copy(
                        isActionSheetOpen = true,
                        actions = listOf(ActionSheetMenu.CREATE_BY_SEED)
                    )
                }
            }

            HomeEvent.ShowRestoreWalletSheet -> {
                _guestUiState.update {
                    it.copy(
                        isActionSheetOpen = true,
                        actions = listOf(ActionSheetMenu.RESTORE_BY_SEED)
                    )
                }
            }

            HomeEvent.CloseActionSheet -> {
                _guestUiState.update {
                    it.copy(isActionSheetOpen = false)
                }
            }

            HomeEvent.OnCreateWallet -> {
                _guestUiState.update {
                    it.copy(isActionSheetOpen = false)
                }
                dispatchEvent(event)
            }

            HomeEvent.OnRestoreWallet -> {
                _guestUiState.update {
                    it.copy(isActionSheetOpen = false)
                }
                dispatchEvent(event)
            }
        }
    }
}