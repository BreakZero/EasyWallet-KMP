package com.easy.wallet.model.asset

import kotlin.native.ObjCName

@ObjCName("AssetNetwork")
data class AssetNetwork(
    val networkName: String,
    val rpcUrl: String,
    val decimalPlace: Int,
    val explorerUrl: String?
)
