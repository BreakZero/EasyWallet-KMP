package com.easy.wallet.network.source.okx.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class MessageOption(
  @SerialName("args")
  val args: List<OptionArg>,
  @SerialName("op")
  val op: String
)

@Serializable
internal data class OptionArg(
  @SerialName("channel")
  val channel: String,
  @SerialName("instId")
  val instId: String
)
