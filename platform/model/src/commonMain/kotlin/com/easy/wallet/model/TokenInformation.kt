package com.easy.wallet.model

import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@OptIn(ExperimentalObjCName::class)
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
