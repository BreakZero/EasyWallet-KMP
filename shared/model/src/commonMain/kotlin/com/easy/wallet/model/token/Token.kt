package com.easy.wallet.model.token

import com.easy.wallet.model.enums.CoinVals
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName("Token")
data class Token(
    val id: String,
    val name: String,
    val symbol: String,
    val decimals: Int,
    val type: CoinVals.CoinType,
    val address: String,
    val logoURI: String
)

@OptIn(ExperimentalObjCName::class)
@ObjCName("TokenWithBalance")
data class TokenWithBalance(
    val token: Token,
    val balance: String
)
