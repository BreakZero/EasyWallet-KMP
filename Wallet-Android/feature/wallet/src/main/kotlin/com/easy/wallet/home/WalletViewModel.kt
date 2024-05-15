package com.easy.wallet.home

import androidx.lifecycle.viewModelScope
import com.easy.wallet.android.core.BaseViewModel
import com.easy.wallet.shared.data.multiwallet.MultiWalletRepository
import com.easy.wallet.shared.domain.AllAssetDashboardUseCase
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

internal class WalletViewModel(
    private val multiWalletRepository: MultiWalletRepository,
    private val assetDashboardUseCase: AllAssetDashboardUseCase
) : BaseViewModel<WalletEvent>() {
    private val _isRefreshing = MutableStateFlow(true)

    private val _walletUiState = MutableStateFlow<WalletUiState>(WalletUiState.Initial)

    val isRefreshing = _isRefreshing.asStateFlow()
    val homeUiState = _walletUiState.asStateFlow()

    init {
        fetch()
    }

    private fun fetch() {
        multiWalletRepository.forActiveOneStream().flatMapConcat { wallet ->
            if (wallet != null) {
                assetDashboardUseCase(wallet).onStart {
                    _isRefreshing.update { true }
                }.onCompletion {
                    _isRefreshing.update { false }
                }.map { WalletUiState.WalletUiState(it) as WalletUiState }
            } else {
                flow {
                    _isRefreshing.update { false }
                    emit(WalletUiState.GuestUserUiState)
                }
            }
        }.onEach { latestState ->
            _walletUiState.update { latestState }
        }.launchIn(viewModelScope)
    }

    override fun handleEvent(event: WalletEvent) {
        when (event) {
            is WalletEvent.OnRefreshing -> {
                fetch()
            }

            else -> dispatchEvent(event)
        }
    }
}
