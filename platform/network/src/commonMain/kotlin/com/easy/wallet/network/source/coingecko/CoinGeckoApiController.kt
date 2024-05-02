package com.easy.wallet.network.source.coingecko

import com.easy.wallet.model.CoinInformation
import com.easy.wallet.model.CoinMarketInformation
import com.easy.wallet.network.tryGet
import com.easy.wallet.network.source.coingecko.dto.CoinGeckoMarketChartDto
import com.easy.wallet.network.source.coingecko.dto.CoinGeckoMarketsDto
import com.easy.wallet.network.source.coingecko.dto.CoinGeckoSearchTrendingDto
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter

class CoinGeckoApiController internal constructor(
    private val httpClient: HttpClient
) : CoinGeckoApi {
    override suspend fun getCoinsMarkets(
        currency: String,
        page: Int,
        numCoinsPerPage: Int,
        order: String,
        includeSparkline7dData: Boolean,
        priceChangePercentageIntervals: String,
        coinIds: String?
    ): List<CoinMarketInformation> {
        return httpClient.tryGet<List<CoinGeckoMarketsDto>>("coins/markets", isThrows = true) {
            parameter("vs_currency", currency)
            parameter("page", page)
            parameter("per_page", numCoinsPerPage)
            parameter("order", order)
            parameter("sparkline", includeSparkline7dData)
            parameter("price_change_percentage", priceChangePercentageIntervals)
            parameter("ids", coinIds)
        }?.map {
            CoinMarketInformation(
                id = it.id,
                symbol = it.symbol,
                name = it.name,
                image = it.image,
                currentPrice = it.currentPrice,
                priceChangePercentage24h = it.priceChangePercentage24h,
                price = it.sparklineIn7d?.price ?: emptyList()
            )
        } ?: emptyList()
    }

    override suspend fun getCoinMarketChart(
        coinId: String,
        currency: String,
        days: String
    ): Any? {
        return httpClient.tryGet<CoinGeckoMarketChartDto>("coins/$coinId/market_chart") {
            parameter("vs_currency", currency)
            parameter("days", days)
        }
    }

    override suspend fun getSearchTrending(): List<CoinInformation> {
        return httpClient.tryGet<CoinGeckoSearchTrendingDto>("search/trending")?.coins?.map { it.item }?.map {
            CoinInformation(
                id = it.id,
                coinId = it.coinId,
                name = it.name,
                symbol = it.symbol,
                thumb = it.thumb,
                largeImage = it.large
            )
        } ?: emptyList()
    }
}
