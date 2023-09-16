package com.easy.wallet.home

import androidx.lifecycle.viewModelScope
import com.easy.wallet.android.core.BaseViewModel
import com.easy.wallet.data.global.HDWalletInstant
import com.easy.wallet.data.multiwallet.MultiWalletRepository
import com.easy.wallet.data.usecase.DashboardUseCase
import com.easy.wallet.home.component.ActionSheetMenu
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

internal class HomeViewModel(
    multiWalletRepository: MultiWalletRepository,
    private val dashboardUseCase: DashboardUseCase,
    private val hdWalletInstant: HDWalletInstant
) : BaseViewModel<HomeEvent>() {

    private val _guestUiState = MutableStateFlow(
        HomeUiState.GuestUiState(),
    )

    val homeUiState = multiWalletRepository.forActivatedOne().flatMapConcat {
        it?.let {
            hdWalletInstant.loadInMemory(it.mnemonic, it.passphrase)
            dashboardUseCase().map { HomeUiState.WalletUiState(it) }
        } ?: _guestUiState
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(3_000), HomeUiState.Fetching)

    override fun handleEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.ShowCreateWalletSheet -> {
                _guestUiState.update {
                    it.copy(
                        isActionSheetOpen = true,
                        actions = listOf(ActionSheetMenu.CREATE_BY_SEED),
                    )
                }
            }

            HomeEvent.ShowRestoreWalletSheet -> {
                _guestUiState.update {
                    it.copy(
                        isActionSheetOpen = true,
                        actions = listOf(ActionSheetMenu.RESTORE_BY_SEED),
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
            HomeEvent.ClickSettings -> dispatchEvent(event)
        }
    }
}
