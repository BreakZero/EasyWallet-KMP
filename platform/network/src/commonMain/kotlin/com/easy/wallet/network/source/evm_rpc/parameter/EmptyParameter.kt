package com.easy.wallet.network.source.evm_rpc.parameter


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class EmptyParameter(
    @SerialName("id")
    val id: Int,
    @SerialName("jsonrpc")
    val jsonrpc: String,
    @SerialName("method")
    val method: String,
    @SerialName("params")
    val params: List<String>
)
