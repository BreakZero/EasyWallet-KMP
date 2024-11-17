package com.easy.wallet.model

import kotlin.native.ObjCName

@ObjCName("CoinMarketInformation")
data class CoinMarketInformation(
  val id: String,
  val symbol: String,
  val name: String,
  val image: String,
  val currentPrice: Double,
  val priceChangePercentage24h: Double,
  val price: List<Double>
)
