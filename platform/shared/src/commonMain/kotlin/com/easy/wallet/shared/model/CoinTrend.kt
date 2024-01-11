package com.easy.wallet.shared.model

data class CoinTrend(
    val id: String,
    val coinId: Long,
    val name: String,
    val symbol: String,
    val thumb: String,
    val largeImage: String
)
