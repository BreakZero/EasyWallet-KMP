package com.easy.wallet.send

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.easy.wallet.model.asset.AssetBalance
import com.easy.wallet.shared.model.fees.FeeModel

sealed interface SendUiEvent {
    data class NavigateTo(val destination: String) : SendUiEvent
    data object NavigateBack: SendUiEvent

    data class ShowErrorMessage(val message: String): SendUiEvent

    data object Backspace : SendUiEvent
    data class EnterDigital(val char: String) : SendUiEvent
    data object MaxAmount : SendUiEvent
    data object BuildTransactionPlan : SendUiEvent

    data class OnSigningTransaction(val chainIdHex: String?) : SendUiEvent
    data class DestinationChanged(val text: String) : SendUiEvent
    data class OnFeeChanged(val fee: FeeModel): SendUiEvent
}

internal sealed interface DestinationUiState {
    data object Empty : DestinationUiState
    data object Error : DestinationUiState
    data object Success : DestinationUiState
}

@Stable
internal data class AmountUiState(
    val enterAmount: String,
    val insufficientBalance: Boolean = false
)

@Stable
internal data class OverviewUiState(
    val amount: String = "",
    val destination: String = "",
    val fees: List<FeeModel> = emptyList(),
    val selectedFee: FeeModel? = fees.firstOrNull()
)

internal sealed interface SendingBasicUiState {
    data object Loading : SendingBasicUiState

    data object Error : SendingBasicUiState

    @Immutable
    data class PrepBasicInfo(
        val assetBalance: AssetBalance
    ) : SendingBasicUiState
}
