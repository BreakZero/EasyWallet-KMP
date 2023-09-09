package com.easy.wallet.model.token

data class Token(
    val coinId: String,
    val coinName: String,
    val symbol: String,
    val decimals: Int?,
    val type: String
)
