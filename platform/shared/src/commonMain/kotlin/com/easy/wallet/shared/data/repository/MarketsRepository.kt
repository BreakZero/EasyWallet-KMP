package com.easy.wallet.shared.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.easy.wallet.model.CoinInformation
import com.easy.wallet.model.CoinMarketInformation
import com.easy.wallet.network.source.coingecko.CoinGeckoApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MarketsRepository internal constructor(
    private val coinGeckoApi: CoinGeckoApi
) {
    fun topCoinsByPaging(
        currency: String = "usd"
    ): Pager<Int, CoinMarketInformation> {
        return Pager(
            config = PagingConfig(pageSize = Int.MAX_VALUE, prefetchDistance = 2),
            pagingSourceFactory = {
                MarketDataPagingSource(currency, coinGeckoApi)
            }
        )
    }

    fun searchTrends(): Flow<List<CoinInformation>> {
        return flow {
            val trends = coinGeckoApi.getSearchTrending()
            emit(trends)
        }
    }
}

internal class MarketDataPagingSource(
    private val currency: String,
    private val coinGeckoApi: CoinGeckoApi
) : PagingSource<Int, CoinMarketInformation>() {
    override fun getRefreshKey(state: PagingState<Int, CoinMarketInformation>): Int? {
        return state.anchorPosition ?: 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CoinMarketInformation> {
        return try {
            val currentPage = params.key ?: 1
            val topCoins = coinGeckoApi.getCoinsMarkets(
                currency = currency,
                page = currentPage,
                numCoinsPerPage = 100,
                order = "market_cap_desc",
                includeSparkline7dData = true,
                priceChangePercentageIntervals = "",
                coinIds = null
            )
            LoadResult.Page(
                data = topCoins,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (topCoins.isEmpty()) null else currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
