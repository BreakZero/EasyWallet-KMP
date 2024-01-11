package com.easy.wallet.shared.data.repository

import com.easy.wallet.network.source.coingecko.CoinGeckoApi
import com.easy.wallet.shared.model.MarketCoin
import com.easy.wallet.shared.model.CoinTrend
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MarketsRepository internal constructor(
    private val coinGeckoApi: CoinGeckoApi
) {
    fun fetchTopCoins(): Flow<List<MarketCoin>> {
        return flow {
            val topCoins = coinGeckoApi.getCoinsMarkets(
                currency = "usd",
                page = 1,
                numCoinsPerPage = 100,
                order = "market_cap_desc",
                includeSparkline7dData = true,
                priceChangePercentageIntervals = "",
                coinIds = null
            )
            emit(topCoins.map {
                MarketCoin(
                    id = it.id,
                    symbol = it.symbol,
                    name = it.name,
                    image = it.image,
                    currentPrice = it.currentPrice,
                    priceChangePercentage24h = it.priceChangePercentage24h,
                    price = it.sparklineIn7d?.price ?: emptyList()
                )
            })
        }
    }

    fun searchTrends(): Flow<List<CoinTrend>> {
        return flow {
            val trends = coinGeckoApi.getSearchTrending().coins.map { it.item }
            emit(
                trends.map {
                    CoinTrend(
                        id = it.id,
                        coinId = it.coinId,
                        name = it.name,
                        symbol = it.symbol,
                        thumb = it.thumb,
                        largeImage = it.large
                    )
                }
            )
        }
    }
}
