package com.easy.wallet.model

import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName("TokenInformation")
data class TokenBasicResult(
    val tokenId: String,
    val symbol: String,
    val name: String,
    val decimals: Int,
    val address: String
)
