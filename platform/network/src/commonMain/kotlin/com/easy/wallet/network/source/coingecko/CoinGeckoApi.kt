package com.easy.wallet.network.source.coingecko

import com.easy.wallet.network.source.coingecko.dto.CoinGeckoMarketChartDto
import com.easy.wallet.network.source.coingecko.dto.CoinGeckoMarketsDto
import com.easy.wallet.network.source.coingecko.dto.CoinGeckoSearchTrendingDto

interface CoinGeckoApi {
    suspend fun getCoinsMarkets(
        currency: String = "usd",
        page: Int = 1,
        numCoinsPerPage: Int = 100,
        order: String = "market_cap_desc",
        includeSparkline7dData: Boolean = false,
        priceChangePercentageIntervals: String = "",
        coinIds: String? = null
    ): List<CoinGeckoMarketsDto>
    suspend fun getCoinMarketChart(
        coinId: String,
        currency: String = "usd",
        days: String = "1"
    ): CoinGeckoMarketChartDto

    suspend fun getSearchTrending(): CoinGeckoSearchTrendingDto
}
