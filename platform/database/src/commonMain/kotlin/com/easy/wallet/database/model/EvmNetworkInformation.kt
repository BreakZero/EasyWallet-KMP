package com.easy.wallet.database.model

import kotlinx.serialization.Serializable

@Serializable
data class EvmNetworkInformation(
    val networkName: String,
    val rpcUrl: String,
    val symbol: String,
    val explorerUrl: String?
)
