package com.easy.wallet.model

import kotlin.native.ObjCName

@ObjCName("TokenInformation")
data class TokenInformation(
    val id: String,
    val chainName: String,
    val name: String,
    val symbol: String,
    val decimals: Int,
    val contract: String?,
    val iconUri: String,
    val chainIdHex: String?,
    val isActive: Boolean
)
