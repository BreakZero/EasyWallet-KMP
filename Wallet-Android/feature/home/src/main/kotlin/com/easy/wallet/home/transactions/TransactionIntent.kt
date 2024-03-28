package com.easy.wallet.home.transactions

internal sealed interface TransactionDashboardUiState {
    data object Loading: TransactionDashboardUiState
    data class Success(
        val amount: String,
        val trends: List<String>
    ): TransactionDashboardUiState
}


internal sealed interface TransactionEvent {
    data class ClickSend(val tokenId: String): TransactionEvent
    data class ClickReceive(val tokenId: String): TransactionEvent
}
