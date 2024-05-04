package com.easy.wallet.model.asset

sealed interface CoinModel {
    val id: String
    val name: String
    val symbol: String
    val decimalPlace: Int
    val logoURI: String
    val contract: String?
    val platform: AssetPlatform
}
