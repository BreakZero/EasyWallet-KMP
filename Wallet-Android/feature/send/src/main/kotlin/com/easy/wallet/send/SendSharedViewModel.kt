package com.easy.wallet.send

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.clearText
import androidx.compose.foundation.text2.input.delete
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.easy.wallet.android.core.BaseViewModel
import com.easy.wallet.core.commom.AssetPlatformIdConstant
import com.easy.wallet.send.navigation.CoinArgs
import com.easy.wallet.send.navigation.sendOverviewRoute
import com.easy.wallet.send.navigation.sendPendingRoute
import com.easy.wallet.shared.domain.CoinBalanceUseCase
import com.easy.wallet.shared.domain.GetAssetCoinInfoUseCase
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
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update

@OptIn(ExperimentalFoundationApi::class)
internal class SendSharedViewModel(
    savedStateHandle: SavedStateHandle,
    getAssetCoinInfoUseCase: GetAssetCoinInfoUseCase,
    coinBalanceUseCase: CoinBalanceUseCase,
    private val transactionPlanUseCase: TransactionPlanUseCase,
    private val transactionSigningUseCase: TransactionSigningUseCase
) : BaseViewModel<SendUiEvent>() {

    private val coinArgs: CoinArgs = CoinArgs(savedStateHandle)
    private val coinId = coinArgs.coinId

    val destination = TextFieldState("")
    private val _amount = TextFieldState("0")

    // prep information for sending coin
    val basicInfoUiState = coinBalanceUseCase(coinId).map { assetBalance ->
        SendingBasicUiState.PrepBasicInfo(assetBalance)
    }.stateIn(viewModelScope, SharingStarted.Lazily, SendingBasicUiState.Loading)

    // enter destination page ui state, check enter address is valid or not
    val destinationUiState = combine(
        destination.textAsFlow(),
        getAssetCoinInfoUseCase(coinId)
    ) { address, assetCoin ->
        val coinType = when (assetCoin.platform.id) {
            AssetPlatformIdConstant.PLATFORM_ETHEREUM,
            AssetPlatformIdConstant.PLATFORM_ETHEREUM_SEPOLIA -> CoinType.Ethereum

            else -> CoinType.Bitcoin
        }
        val isAddressValid = AnyAddress.isValid(
            address.toString(),
            coinType
        )
        if (isAddressValid) DestinationUiState.Success else DestinationUiState.Error
    }.stateIn(viewModelScope, SharingStarted.Lazily, DestinationUiState.Empty)

    // enter amount page ui state, check balance is enough or not
    val amountUiState = combine(
        _amount.textAsFlow(),
        coinBalanceUseCase(coinId)
    ) { amount, assetBalance ->
        val insufficientBalance =
            assetBalance.balance.toDouble() < (amount.toString().toDoubleOrNull() ?: 0.0)
        AmountUiState(
            enterAmount = amount.toString(),
            insufficientBalance = insufficientBalance
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(3_000), AmountUiState("0"))

    // overview ui state, generate from build transaction plan, then sign transaction plan with selected fee to broadcast
    private val _overviewUiState = MutableStateFlow(OverviewUiState())
    val overviewUiState = _overviewUiState.asStateFlow()
    private fun buildPlan(onCompletion: () -> Unit) {
        transactionPlanUseCase(
            coinId = coinId,
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
        }.onCompletion { _ ->
            onCompletion()
        }.catch {
            // handle error
            it.printStackTrace()
        }.launchIn(viewModelScope)
    }

    private fun signAndPush(chainId: String, onCompletion: (Throwable?) -> Unit) {
        with(_overviewUiState.value) {
            transactionSigningUseCase(
                coinId = coinId,
                toAddress = destination,
                amount = _amount.text.toString(),
                fee = selectedFee!!
            ).onEach {
                println("===== $it")
            }.onCompletion { cause ->
                println("====== onCompletion")
                onCompletion(cause)
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
                    if (basicInfoUiState.value is SendingBasicUiState.PrepBasicInfo) {
                        append((basicInfoUiState.value as SendingBasicUiState.PrepBasicInfo).assetBalance.balance)
                    }
                }
            }

            SendUiEvent.BuildTransactionPlan -> {
                buildPlan {
                    dispatchEvent(SendUiEvent.NavigateTo(sendOverviewRoute))
                }
            }

            is SendUiEvent.OnSigningTransaction -> {
                // Sepolia as default
                signAndPush(event.chainIdHex ?: "0xaa36a7") {
                    if (it == null) {
                        dispatchEvent(SendUiEvent.NavigateTo(sendPendingRoute))
                    } else {
                        dispatchEvent(
                            SendUiEvent.ShowErrorMessage(
                                it.message ?: "broadcast transaction failed"
                            )
                        )
                    }
                }
            }

            is SendUiEvent.DestinationChanged -> {
                destination.clearText()
                destination.edit {
                    append(event.text)
                }
            }

            is SendUiEvent.OnFeeChanged -> {
                _overviewUiState.update { it.copy(selectedFee = event.fee) }
            }

            is SendUiEvent.NavigateTo, SendUiEvent.NavigateBack,
            is SendUiEvent.ShowErrorMessage -> dispatchEvent(event)
        }
    }
}
