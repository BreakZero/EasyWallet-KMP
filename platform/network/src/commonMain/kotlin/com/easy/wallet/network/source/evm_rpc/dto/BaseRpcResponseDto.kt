package com.easy.wallet.network.source.evm_rpc.dto


import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class BaseRpcResponseDto<T>(
    @SerialName("id")
    val id: Int,
    @SerialName("jsonrpc")
    val jsonrpc: String,
    @SerialName("result")
    val result: T? = null,
    @SerialName("error")
    val error: RpcError? = null
)

@Serializable
internal data class RpcError(
    @SerialName("code")
    val code: Int,
    @SerialName("message")
    val message: String
)
