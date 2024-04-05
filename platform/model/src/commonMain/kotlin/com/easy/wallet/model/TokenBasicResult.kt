package com.easy.wallet.model

import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName("TokenBasicResult")
data class TokenBasicResult(
    val symbol: String,
    val name: String,
    val decimals: Int,
    val address: String,
    val iconUri: String,
    val contract: String?,
    val chainName: String
)
