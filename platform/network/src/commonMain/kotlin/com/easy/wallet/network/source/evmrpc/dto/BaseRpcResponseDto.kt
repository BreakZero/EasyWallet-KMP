package com.easy.wallet.network.source.evmrpc.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class BaseRpcResponseDto<T>(
  @SerialName("id")
  val id: Int,
  @SerialName("jsonrpc")
  val jsonrpc: String,
  @SerialName("result")
  val result: T
)

@Serializable
internal data class RpcError(
  @SerialName("code")
  val code: Int,
  @SerialName("message")
  val message: String
)

@Serializable
internal data class RpcErrorResponse(
  @SerialName("id")
  val id: Int,
  @SerialName("jsonrpc")
  val jsonrpc: String,
  @SerialName("error")
  val error: RpcError?
)
