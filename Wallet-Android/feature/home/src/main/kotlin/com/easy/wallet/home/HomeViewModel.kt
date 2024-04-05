package com.easy.wallet.home

import androidx.lifecycle.viewModelScope
import com.easy.wallet.android.core.BaseViewModel
import com.easy.wallet.shared.data.multiwallet.MultiWalletRepository
import com.easy.wallet.shared.domain.DashboardUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

internal class HomeViewModel(
    multiWalletRepository: MultiWalletRepository,
    private val dashboardUseCase: DashboardUseCase
) : BaseViewModel<HomeEvent>() {
    val isWalletExist = multiWalletRepository.forActivatedOne().map { it != null }
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(3_000), false)

    val homeUiState = multiWalletRepository.forActivatedOne().filterNotNull().flatMapConcat {
        dashboardUseCase(it).map { HomeUiState.WalletUiState(it) }
    }.stateIn(viewModelScope, SharingStarted.Lazily, HomeUiState.Loading)

    override fun handleEvent(event: HomeEvent) {
        dispatchEvent(event)
    }
}
