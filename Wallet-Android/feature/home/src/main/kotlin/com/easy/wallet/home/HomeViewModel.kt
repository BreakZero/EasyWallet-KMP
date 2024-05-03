package com.easy.wallet.home

import androidx.lifecycle.viewModelScope
import com.easy.wallet.android.core.BaseViewModel
import com.easy.wallet.shared.data.multiwallet.MultiWalletRepository
import com.easy.wallet.shared.domain.DashboardUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.update

internal class HomeViewModel(
    private val multiWalletRepository: MultiWalletRepository,
    private val dashboardUseCase: DashboardUseCase
) : BaseViewModel<HomeEvent>() {
    private val _isRefreshing = MutableStateFlow(true)

    private val _homeUiState = MutableStateFlow<HomeUiState>(HomeUiState.Initial)

    val isRefreshing = _isRefreshing.asStateFlow()
    val homeUiState = _homeUiState.asStateFlow()

    init {
        fetch()
    }

    private fun fetch() {
        multiWalletRepository.forActiveOneStream().flatMapConcat { wallet ->
            if (wallet != null) {
                dashboardUseCase(wallet).onStart {
                    _isRefreshing.update { true }
                }.onCompletion {
                    _isRefreshing.update { false }
                }.map { HomeUiState.WalletUiState(it) as HomeUiState }
            } else {
                flow {
                    _isRefreshing.update { false }
                    emit(HomeUiState.GuestUserUiState)
                }
            }
        }.onEach { latestState ->
            _homeUiState.update { latestState }
        }.launchIn(viewModelScope)
    }

    override fun handleEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.OnRefreshing -> {
                fetch()
            }

            else -> dispatchEvent(event)
        }
    }
}
