package com.easy.wallet.database.dao

import com.easy.wallet.database.CoinEntity
import com.easy.wallet.model.asset.AssetCoin
import kotlinx.coroutines.flow.Flow

interface CoinDao {
    suspend fun insert(vararg coins: CoinEntity)

    suspend fun findAllStream(): Flow<List<AssetCoin>>

    suspend fun findAllByPlatformStream(platformId: String): Flow<List<AssetCoin>>
}
