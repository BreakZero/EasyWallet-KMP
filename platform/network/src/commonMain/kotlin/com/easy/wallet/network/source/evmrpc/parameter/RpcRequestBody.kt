package com.easy.wallet.network.source.evmrpc.parameter

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable(with = RpcRequestBodySerializer::class)
internal data class RpcRequestBody(
  @SerialName("id")
  val id: Int,
  @SerialName("jsonrpc")
  val jsonrpc: String,
  @SerialName("method")
  val method: String,
  @SerialName("params")
  val params: List<Parameter>
)
