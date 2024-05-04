package com.easy.wallet.shared.model

import com.easy.wallet.model.asset.AssetBalance

data class DashboardInformation(
    val fiatSymbol: String,
    val fiatBalance: String,
    val tokens: List<TokenUiModel>
)

data class AllAssetDashboardInformation(
    val fiatSymbol: String,
    val fiatBalance: String,
    val assetBalances: List<AssetBalance>
)
