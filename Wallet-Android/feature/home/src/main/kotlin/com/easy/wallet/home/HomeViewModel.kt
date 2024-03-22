package com.easy.wallet.home

import androidx.lifecycle.viewModelScope
import com.easy.wallet.android.core.BaseViewModel
import com.easy.wallet.home.component.ActionSheetMenu
import com.easy.wallet.shared.data.multiwallet.MultiWalletRepository
import com.easy.wallet.shared.domain.DashboardUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

internal class HomeViewModel(
    multiWalletRepository: MultiWalletRepository,
    private val dashboardUseCase: DashboardUseCase
) : BaseViewModel<HomeEvent>() {

    private val _guestUiState = MutableStateFlow(
        HomeUiState.GuestUiState(),
    )

    val homeUiState = multiWalletRepository.forActivatedOne().flatMapConcat {
        it?.let {
            dashboardUseCase(it).map { HomeUiState.WalletUiState(it) }
        } ?: _guestUiState
    }.stateIn(viewModelScope, SharingStarted.Lazily, HomeUiState.Loading)

    override fun handleEvent(event: HomeEvent) {
        when (event) {
            HomeEvent.OpenCreateWalletActions -> {
                _guestUiState.update {
                    it.copy(
                        actions = listOf(ActionSheetMenu.CREATE_BY_SEED),
                    )
                }
            }

            HomeEvent.OpenRestoreWalletActions -> {
                _guestUiState.update {
                    it.copy(
                        actions = listOf(ActionSheetMenu.RESTORE_BY_SEED),
                    )
                }
            }

            else -> {
                dispatchEvent(event)
            }
        }
    }
}
