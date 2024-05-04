package com.easy.wallet.shared.model

import com.easy.wallet.model.asset.AssetBalance

data class AllAssetDashboardInformation(
    val fiatSymbol: String,
    val fiatBalance: String,
    val assetBalances: List<AssetBalance>
)
