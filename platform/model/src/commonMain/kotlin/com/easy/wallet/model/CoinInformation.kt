package com.easy.wallet.model

import kotlin.native.ObjCName

@ObjCName("CoinInformation")
data class CoinInformation(
    val id: String,
    val coinId: Long,
    val name: String,
    val symbol: String,
    val thumb: String,
    val largeImage: String
)
