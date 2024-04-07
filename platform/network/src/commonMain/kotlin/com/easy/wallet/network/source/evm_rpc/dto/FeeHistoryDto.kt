package com.easy.wallet.network.source.evm_rpc.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class FeeHistoryDto(
    @SerialName("baseFeePerGas")
    val baseFeePerGas: List<String>,
    @SerialName("gasUsedRatio")
    val gasUsedRatio: List<Double>,
    @SerialName("oldestBlock")
    val oldestBlock: String,
    @SerialName("reward")
    val reward: List<List<String>>
)
