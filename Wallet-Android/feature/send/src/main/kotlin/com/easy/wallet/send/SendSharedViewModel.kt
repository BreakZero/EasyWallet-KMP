package com.easy.wallet.send

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.clearText
import androidx.compose.foundation.text2.input.delete
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.easy.wallet.android.core.BaseViewModel
import com.easy.wallet.send.navigation.TokenArgs
import com.easy.wallet.shared.domain.GetToKenBasicInfoUseCase
import com.easy.wallet.shared.domain.TokenBalanceUseCase
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn

@OptIn(ExperimentalFoundationApi::class)
internal class SendSharedViewModel(
    savedStateHandle: SavedStateHandle,
    basicInfoUseCase: GetToKenBasicInfoUseCase,
    tokenBalanceUseCase: TokenBalanceUseCase
) : BaseViewModel<SendUiEvent>() {

    private val tokenArgs: TokenArgs = TokenArgs(savedStateHandle)
    private val tokenId = tokenArgs.tokenId

    val destination = TextFieldState("")
    private val _amount = TextFieldState("0")

    val sendUiState = combine(
        basicInfoUseCase(tokenId),
        tokenBalanceUseCase(tokenId),
        destination.textAsFlow(),
        _amount.textAsFlow()
    ) { basicInfo, balance, destination, amount ->
        SendUiState.Success(
            tokenInfo = basicInfo,
            balance = balance,
            recipient = destination.toString(),
            amount = amount.toString()
        ) as SendUiState
    }.catch {
        emit(SendUiState.Error)
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(3_000), SendUiState.Loading)


    override fun handleEvent(event: SendUiEvent) {
        when (event) {
            SendUiEvent.Backspace -> {
                _amount.edit {
                    delete(length - 1, length)
                    if (length == 0) {
                        append("0")
                    }
                }
            }

            SendUiEvent.ClickNext -> dispatchEvent(event)
            is SendUiEvent.EnterDigital -> {
                _amount.edit {
                    when {
                        event.char == "." && _amount.text.contains(".") -> return@edit
                        event.char != "." && _amount.text.contentEquals("0") -> replace(
                            0,
                            1,
                            event.char
                        )

                        else -> append(event.char)
                    }
                }
            }

            SendUiEvent.MaxAmount -> {
                _amount.clearText()
                _amount.edit {
                    val _state = sendUiState.value
                    if (_state is SendUiState.Success) append(_state.balance)
                }
            }
        }
    }
}
