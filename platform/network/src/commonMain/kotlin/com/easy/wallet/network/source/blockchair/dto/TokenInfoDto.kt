package com.easy.wallet.network.source.blockchair.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class TokenInfoDto(
  @SerialName("balance")
  val balance: String,
  @SerialName("balance_approximate")
  val balanceApproximate: Double,
  @SerialName("token_address")
  val tokenAddress: String,
  @SerialName("token_decimals")
  val tokenDecimals: Int,
  @SerialName("token_name")
  val tokenName: String,
  @SerialName("token_symbol")
  val tokenSymbol: String
)
