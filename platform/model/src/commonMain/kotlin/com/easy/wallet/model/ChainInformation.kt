package com.easy.wallet.model

import com.easy.wallet.model.enums.CoinVals

data class ChainInformation(
    val id: Long,
    val name: String,
    val website: String,
    val explorer: String?,
    val rpcUrl: String,
    val chainId: String?
)
