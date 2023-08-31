package com.easy.wallet.onboard.create

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.toMutableStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.easy.wallet.data.multiwallet.MultiWalletRepository
import com.easy.wallet.datastore.UserPasswordStorage
import com.easy.wallet.domain.hdwallet.CreateWalletUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class CreateWalletViewModel(
    private val userStorage: UserPasswordStorage,
    private val multiWalletRepository: MultiWalletRepository,
    private val createWalletUseCase: CreateWalletUseCase
) : ViewModel() {

    private val _passwordUiState = MutableStateFlow(PasswordUiState())
    val passwordUiState = _passwordUiState.asStateFlow()

    var seedPhrase = mutableStateListOf<String>()
        private set

    private val _uiEventChannel = Channel<CreateWalletUiEvent>()
    val uiEvent = _uiEventChannel.receiveAsFlow()
    private fun dispatchUiEvent(uiEvent: CreateWalletUiEvent) {
        viewModelScope.launch {
            if (uiEvent == CreateWalletUiEvent.OnCreateSuccess) {
                userStorage.putPassword(
                    dispatcher = Dispatchers.IO,
                    _passwordUiState.value.password
                )
            }
            _uiEventChannel.send(uiEvent)
        }
    }

    fun onEvent(event: CreateWalletEvent) {
        when (event) {
            is CreateWalletEvent.OnPasswordChanged -> {
                _passwordUiState.update {
                    it.copy(password = event.password)
                }
            }

            is CreateWalletEvent.OnConfirmPasswordChanged -> {
                _passwordUiState.update {
                    it.copy(confirmPassword = event.confirm)
                }
            }

            is CreateWalletEvent.OnCheckedTermOfServiceChanged -> {
                _passwordUiState.update {
                    it.copy(isTermsOfServiceAgreed = event.checked)
                }
            }

            is CreateWalletEvent.NextToSecure -> dispatchUiEvent(CreateWalletUiEvent.NextToSecure)
            is CreateWalletEvent.Close -> dispatchUiEvent(CreateWalletUiEvent.Close)
            is CreateWalletEvent.NextToCheckSeed -> {
                seedPhrase = createWalletUseCase().split(" ").toMutableStateList()
                dispatchUiEvent(CreateWalletUiEvent.NextToCheckSeed)
            }

            is CreateWalletEvent.OnCreateWallet -> {
                viewModelScope.launch {
                    multiWalletRepository.insertOne(seedPhrase.toList().joinToString(separator = " "), "") {
                        dispatchUiEvent(CreateWalletUiEvent.OnCreateSuccess)
                    }
                }
            }

            else -> Unit
        }
    }
}