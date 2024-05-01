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
import com.easy.wallet.shared.domain.TransactionPlanUseCase
import com.easy.wallet.shared.domain.TransactionSigningUseCase
import com.easy.wallet.shared.model.fees.FeeModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

@OptIn(ExperimentalFoundationApi::class)
internal class SendSharedViewModel(
    savedStateHandle: SavedStateHandle,
    basicInfoUseCase: GetToKenBasicInfoUseCase,
    tokenBalanceUseCase: TokenBalanceUseCase,
    private val transactionPlanUseCase: TransactionPlanUseCase,
    private val transactionSigningUseCase: TransactionSigningUseCase
) : BaseViewModel<SendUiEvent>() {

    private val tokenArgs: TokenArgs = TokenArgs(savedStateHandle)
    private val tokenId = tokenArgs.tokenId

    val destination = TextFieldState("")
    private val _amount = TextFieldState("0")

    private val _fees = MutableStateFlow(emptyList<FeeModel>())
    val fees = _fees.asStateFlow()

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
    }.stateIn(viewModelScope, SharingStarted.Lazily, SendUiState.Loading)


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

            SendUiEvent.TransactionPrep -> {
                prepFee(
                    tokenId = tokenId,
                    toAddress = destination.text.toString(),
                    amount = _amount.text.toString()
                ) {
                    dispatchEvent(SendUiEvent.ClickNext)
                }
            }

            is SendUiEvent.OnSigningTransaction -> {
                transactionSigningUseCase(
                    tokenId = tokenId,
                    chainId = event.chainIdHex ?: "0x1",
                    toAddress = destination.text.toString(),
                    amount = _amount.text.toString(),
                    fee = _fees.value[1]
                ).onEach {
                    println("===== $it")
                }.catch {
                    it.printStackTrace()
                }.launchIn(viewModelScope)
            }

            is SendUiEvent.DestinationChanged -> {
                destination.clearText()
                destination.edit {
                    append(event.text)
                }
            }
        }
    }

    private fun prepFee(
        tokenId: String,
        toAddress: String,
        amount: String,
        onCompleted: () -> Unit
    ) {
        transactionPlanUseCase(tokenId, toAddress, amount).onEach { fees ->
            _fees.update { fees }
            onCompleted()
        }.catch {
            println("===== ${it.message}")
        }.launchIn(viewModelScope)
    }
}
