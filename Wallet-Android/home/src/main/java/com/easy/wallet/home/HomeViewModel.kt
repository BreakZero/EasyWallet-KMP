package com.easy.wallet.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easy.wallet.home.component.ActionSheetMenu
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    internal val homeUiState = _uiState.asStateFlow()
    private val _uiEventChannel = Channel<HomeUiEvent>()
    internal val uiEvent = _uiEventChannel.receiveAsFlow()

    internal fun handleUiEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.ShowCreateWalletSheet -> {
                _uiState.update {
                    it.copy(
                        isActionSheetOpen = true,
                        actions = listOf(ActionSheetMenu.CREATE_BY_SEED)
                    )
                }
            }
            HomeEvent.ShowRestoreWalletSheet -> {
                _uiState.update {
                    it.copy(
                        isActionSheetOpen = true,
                        actions = listOf(ActionSheetMenu.RESTORE_BY_SEED)
                    )
                }
            }
            HomeEvent.CloseActionSheet -> {
                _uiState.update {
                    it.copy(isActionSheetOpen = false)
                }
            }
            HomeEvent.OnCreateWallet -> {
                _uiState.update {
                    it.copy(isActionSheetOpen = false)
                }
                dispatchUiEvent(HomeUiEvent.OnCreateWallet)
            }
            HomeEvent.OnRestoreWallet -> {
                _uiState.update {
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