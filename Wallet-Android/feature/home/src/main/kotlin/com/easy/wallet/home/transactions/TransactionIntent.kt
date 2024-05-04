package com.easy.wallet.home.transactions

import com.easy.wallet.model.asset.AssetBalance

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
    data class ClickSend(val tokenId: String): TransactionEvent
    data class ClickReceive(val tokenId: String): TransactionEvent
}
