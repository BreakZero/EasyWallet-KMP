package com.easy.wallet.home.transactions

import com.easy.wallet.model.asset.AssetBalance
import com.easy.wallet.model.asset.CoinModel

internal sealed interface TransactionDashboardUiState {
    data object Loading: TransactionDashboardUiState

    data object Error: TransactionDashboardUiState
    data class Success(
        val assetBalance: AssetBalance,
        val trends: List<String>
    ): TransactionDashboardUiState
}


internal sealed interface TransactionEvent {

    data class ShowSnackbar(val message: String): TransactionEvent

    data object PopBack: TransactionEvent
    data class ClickSend(val coinModel: CoinModel): TransactionEvent
}
