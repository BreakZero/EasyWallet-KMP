package com.easy.wallet.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easy.wallet.data.hdwallet.HDWalletInMemory
import com.easy.wallet.data.multiwallet.MultiWalletRepository
import com.easy.wallet.data.token.TokenRepository
import com.easy.wallet.home.component.ActionSheetMenu
import com.trustwallet.core.CoinType
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel(
    multiWalletRepository: MultiWalletRepository,
    private val tokenRepository: TokenRepository,
    private val hdWalletInMemory: HDWalletInMemory
) : ViewModel() {

    private val _guestUiState = MutableStateFlow(
        GuestUiState()
    )
    internal val guestUiState = _guestUiState.asStateFlow()

    private val _walletUiState = MutableStateFlow(WalletUiState(""))
    internal val walletUiState = _walletUiState.asStateFlow()

    internal val hasSetup = multiWalletRepository.forActivatedOne().map {
        it?.let {
            hdWalletInMemory.loadToMemory(it.mnemonic, it.passphrase)
//            val address = kotlin.runCatching {
//                hdWalletInMemory.hdWallet().getAddressForCoin(CoinType.Ethereum)
//            }.getOrElse { "" }
//            _walletUiState.update {
//                it.copy(address = address)
//            }
            true
        } ?: false
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(3000), true)

    private val _uiEventChannel = Channel<HomeUiEvent>()
    internal val uiEvent = _uiEventChannel.receiveAsFlow()

    internal fun handleUiEvent(event: HomeEvent) {
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
                dispatchUiEvent(HomeUiEvent.OnCreateWallet)
            }

            HomeEvent.OnRestoreWallet -> {
                _guestUiState.update {
                    it.copy(isActionSheetOpen = false)
                }
                dispatchUiEvent(HomeUiEvent.OnRestoreWallet)
            }
        }
    }

    private fun dispatchUiEvent(uiEvent: HomeUiEvent) {
        viewModelScope.launch {
            _uiEventChannel.send(uiEvent)
        }
    }
}