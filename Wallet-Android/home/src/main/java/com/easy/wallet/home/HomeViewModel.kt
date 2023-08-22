package com.easy.wallet.home

import androidx.lifecycle.ViewModel
import com.easy.wallet.home.component.ActionSheetMenu
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class HomeViewModel : ViewModel() {
    private val _uiState = MutableStateFlow(HomeUiState())
    internal val homeUiState = _uiState.asStateFlow()

    internal fun handleUiEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.CreateWalletEvent -> {
                _uiState.update {
                    it.copy(
                        isActionSheetOpen = true,
                        actions = listOf(ActionSheetMenu.CREATE_BY_SEED)
                    )
                }
            }
            HomeEvent.ImportWalletEvent -> {
                _uiState.update {
                    it.copy(
                        isActionSheetOpen = true,
                        actions = listOf(ActionSheetMenu.IMPORT_BY_SEED)
                    )
                }
            }
            HomeEvent.CloseActionSheet -> {
                _uiState.update {
                    it.copy(isActionSheetOpen = false)
                }
            }
        }
    }

    private fun dispatchUiEvent(uiEvent: HomeUiEvent) {

    }
}