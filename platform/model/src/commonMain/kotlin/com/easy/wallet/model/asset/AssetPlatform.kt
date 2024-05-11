package com.easy.wallet.model.asset

import kotlin.native.ObjCName

@ObjCName("AssetPlatform")
data class AssetPlatform(
    val id: String,
    val shortName: String,
    val chainIdentifier: String?,
    val isTestNet: Boolean,
    val network: AssetNetwork?
)
