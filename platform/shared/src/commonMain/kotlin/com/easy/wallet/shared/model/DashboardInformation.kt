package com.easy.wallet.shared.model

data class DashboardInformation(
    val fiatSymbol: String,
    val fiatBalance: String,
    val tokens: List<TokenUiModel>
)
