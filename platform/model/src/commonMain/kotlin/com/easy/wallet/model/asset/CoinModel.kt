package com.easy.wallet.model.asset

sealed interface CoinModel {
    val id: String
    val symbol: String
    val name: String
    val logoURI: String
    val contract: String?
    val platform: AssetPlatform
}
