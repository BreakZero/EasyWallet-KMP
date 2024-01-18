package com.easy.wallet.shared.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.easy.wallet.network.source.coingecko.CoinGeckoApi
import com.easy.wallet.shared.model.CoinTrend
import com.easy.wallet.shared.model.MarketCoin
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow

class MarketsRepository internal constructor(
    private val coinGeckoApi: CoinGeckoApi
) {
    fun topCoinsByPaging(
        currency: String = "usd"
    ): Pager<Int, MarketCoin> {
        return Pager(
            config = PagingConfig(pageSize = Int.MAX_VALUE, prefetchDistance = 2),
            pagingSourceFactory = {
                MarketDataPagingSource(currency, coinGeckoApi)
            }
        )
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
        }.catch {
            emit(emptyList())
        }
    }
}

internal class MarketDataPagingSource(
    private val currency: String,
    private val coinGeckoApi: CoinGeckoApi
) : PagingSource<Int, MarketCoin>() {
    override fun getRefreshKey(state: PagingState<Int, MarketCoin>): Int? {
        return state.anchorPosition ?: 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, MarketCoin> {
        return try {
            val currentPage = params.key ?: 1
            val topCoins = coinGeckoApi.getCoinsMarkets(
                currency = currency,
                page = currentPage,
                numCoinsPerPage = 20,
                order = "market_cap_desc",
                includeSparkline7dData = true,
                priceChangePercentageIntervals = "",
                coinIds = null
            ).map {
                MarketCoin(
                    id = it.id,
                    symbol = it.symbol,
                    name = it.name,
                    image = it.image,
                    currentPrice = it.currentPrice,
                    priceChangePercentage24h = it.priceChangePercentage24h,
                    price = it.sparklineIn7d?.price ?: emptyList()
                )
            }
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
