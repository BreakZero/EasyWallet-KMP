package com.easy.wallet.model.transaction

import kotlin.native.HiddenFromObjC

@HiddenFromObjC
data class EthereumTransactionBasic(
    val hash: String,
    val amount:String,
    val recipient: String,
    val sender: String,
    val gasPrice: String,
    val gas: String,
    val gasUsed: String,
    val functionName: String?,
    val datetime: String
)
