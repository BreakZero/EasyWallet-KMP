package com.easy.wallet.send

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.clearText
import androidx.compose.foundation.text2.input.delete
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.easy.wallet.android.core.BaseViewModel
import com.easy.wallet.core.commom.Constants
import com.easy.wallet.send.navigation.TokenArgs
import com.easy.wallet.send.navigation.sendOverviewRoute
import com.easy.wallet.shared.domain.GetToKenBasicInfoUseCase
import com.easy.wallet.shared.domain.TokenBalanceUseCase
import com.easy.wallet.shared.domain.TransactionPlanUseCase
import com.easy.wallet.shared.domain.TransactionSigningUseCase
import com.trustwallet.core.AnyAddress
import com.trustwallet.core.CoinType
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onCompletion
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

    val basicInfoState =
        combine(basicInfoUseCase(tokenId), tokenBalanceUseCase(tokenId)) { basicInfo, balance ->
            SendUiState.PrepBasicInfo(basicInfo, balance)
        }.stateIn(viewModelScope, SharingStarted.Lazily, SendUiState.Loading)

    val destinationUiState =
        combine(destination.textAsFlow(), basicInfoUseCase(tokenId)) { address, basicInfo ->
            val coinType = when (basicInfo.chainName) {
                Constants.ETH_CHAIN_NAME -> CoinType.Ethereum
                else -> CoinType.Bitcoin
            }
            val isAddressValid = AnyAddress.isValid(
                address.toString(),
                coinType
            )
            if (isAddressValid) DestinationUiState.Success else DestinationUiState.Error
        }.stateIn(viewModelScope, SharingStarted.Lazily, DestinationUiState.Empty)

    val amountUiState =
        combine(_amount.textAsFlow(), tokenBalanceUseCase(tokenId)) { amount, balance ->
            val insufficientBalance =
                balance.toDouble() < (amount.toString().toDoubleOrNull() ?: 0.0)
            AmountUiState(
                enterAmount = amount.toString(),
                insufficientBalance = insufficientBalance
            )
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(3_000), AmountUiState("0"))

    private val _overviewUiState = MutableStateFlow(OverviewUiState())
    val overviewUiState = _overviewUiState.asStateFlow()
    private fun buildPlan(
        onCompletion: () -> Unit
    ) {
        transactionPlanUseCase(
            tokenId = tokenId,
            toAddress = destination.text.toString(),
            amount = _amount.text.toString(),
        ).onEach { fees ->
            _overviewUiState.update {
                OverviewUiState(
                    amount = _amount.text.toString(),
                    destination = destination.text.toString(),
                    fees = fees
                )
            }
            onCompletion()
        }.catch {
            // handle error
            it.printStackTrace()
        }.launchIn(viewModelScope)
    }

    private fun signAndPush(chainId: String) {
        with(_overviewUiState.value) {
            transactionSigningUseCase(
                tokenId = tokenId,
                chainId = chainId,
                toAddress = destination,
                amount = _amount.text.toString(),
                // will do
                fee = selectedFee!!
            ).onEach {
                println("===== $it")
            }.catch {
                it.printStackTrace()
            }.launchIn(viewModelScope)
        }
    }

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
                    if (basicInfoState.value is SendUiState.PrepBasicInfo) {
                        append((basicInfoState.value as SendUiState.PrepBasicInfo).balance)
                    }
                }
            }

            SendUiEvent.BuildTransactionPlan -> {
                buildPlan {
                    dispatchEvent(SendUiEvent.NavigateTo(sendOverviewRoute))
                }
            }

            is SendUiEvent.OnSigningTransaction -> {
                signAndPush(event.chainIdHex ?: "0x1")
            }

            is SendUiEvent.DestinationChanged -> {
                destination.clearText()
                destination.edit {
                    append(event.text)
                }
            }

            is SendUiEvent.NavigateTo, SendUiEvent.NavigateBack -> dispatchEvent(event)
        }
    }
}
