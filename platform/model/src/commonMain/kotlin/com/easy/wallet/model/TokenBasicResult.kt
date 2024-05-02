package com.easy.wallet.model

import kotlin.native.ObjCName

@ObjCName("TokenBasicResult")
data class TokenBasicResult(
    val symbol: String,
    val name: String,
    val decimals: Int,
    val address: String,
    val iconUri: String,
    val contract: String?,
    val chainName: String,
    val chainIdHex: String?
)
