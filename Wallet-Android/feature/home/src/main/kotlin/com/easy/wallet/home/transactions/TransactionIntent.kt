package com.easy.wallet.home.transactions

import com.easy.wallet.model.TokenInformation

internal sealed interface TransactionDashboardUiState {
    data object Loading: TransactionDashboardUiState
    data class Success(
        val tokenInformation: TokenInformation,
        val amount: String,
        val trends: List<String>
    ): TransactionDashboardUiState
}


internal sealed interface TransactionEvent {
    data class ClickSend(val tokenId: String): TransactionEvent
    data class ClickReceive(val tokenId: String): TransactionEvent
}
