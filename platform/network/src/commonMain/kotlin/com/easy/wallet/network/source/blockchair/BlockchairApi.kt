package com.easy.wallet.network.source.blockchair

import com.easy.wallet.model.news.News


interface BlockchairApi {
    suspend fun getNewsList(limit: Int, offset: Int): List<News>

    suspend fun getDashboardByAccount(chain: String, account: String): String
}
