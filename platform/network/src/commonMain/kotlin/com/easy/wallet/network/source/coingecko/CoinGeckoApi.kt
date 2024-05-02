package com.easy.wallet.network.source.coingecko

import com.easy.wallet.model.CoinInformation
import com.easy.wallet.model.CoinMarketInformation

interface CoinGeckoApi {
    suspend fun getCoinsMarkets(
        currency: String = "usd",
        page: Int = 1,
        numCoinsPerPage: Int = 100,
        order: String = "market_cap_desc",
        includeSparkline7dData: Boolean = false,
        priceChangePercentageIntervals: String = "",
        coinIds: String? = null
    ): List<CoinMarketInformation>

    suspend fun getCoinMarketChart(
        coinId: String,
        currency: String = "usd",
        days: String = "1"
    ): Any?

    suspend fun getSearchTrending(): List<CoinInformation>
}
