package com.easy.wallet.network.source.coingecko.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class CoinGeckoSearchTrendingDto(
  @SerialName("coins")
  val coins: List<TrendingCoinDto>
)

@Serializable
internal data class TrendingCoinDto(
  @SerialName("item")
  val item: TrendingItemDto
)

@Serializable
internal data class TrendingItemDto(
  @SerialName("coin_id")
  val coinId: Long,
  @SerialName("data")
  val `data`: TrendingDataDto,
  @SerialName("id")
  val id: String,
  @SerialName("large")
  val large: String,
  @SerialName("market_cap_rank")
  val marketCapRank: Int? = null,
  @SerialName("name")
  val name: String,
  @SerialName("price_btc")
  val priceBtc: Double,
  @SerialName("score")
  val score: Int,
  @SerialName("slug")
  val slug: String,
  @SerialName("small")
  val small: String,
  @SerialName("symbol")
  val symbol: String,
  @SerialName("thumb")
  val thumb: String
)

@Serializable
internal data class TrendingDataDto(
  @SerialName("market_cap")
  val marketCap: String,
  @SerialName("market_cap_btc")
  val marketCapBtc: String,
  @SerialName("price")
  val price: String,
  @SerialName("price_btc")
  val priceBtc: String,
  @SerialName("sparkline")
  val sparkline: String,
  @SerialName("total_volume")
  val totalVolume: String,
  @SerialName("total_volume_btc")
  val totalVolumeBtc: String
)
