package com.easy.wallet.model.asset

data class AssetCoin(
    override val id: String,
    override val symbol: String,
    override val name: String,
    override val logoURI: String,
    override val contract: String?,
    override val platform: AssetPlatform,
    val address: String
): CoinModel
