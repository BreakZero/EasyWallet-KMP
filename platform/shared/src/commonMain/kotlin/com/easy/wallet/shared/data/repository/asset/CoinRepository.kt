package com.easy.wallet.shared.data.repository.asset

import com.easy.wallet.model.asset.BasicCoin
import kotlinx.coroutines.flow.Flow

interface CoinRepository {
    fun findAllCoinStream(): Flow<List<BasicCoin>>

    suspend fun findAllCoin(): List<BasicCoin>

    suspend fun findCoinById(coinId: String): BasicCoin?
    suspend fun findCoinsById(coinId: String): List<BasicCoin>
}
