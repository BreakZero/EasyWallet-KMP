package com.easy.wallet.database.dao

import com.easy.wallet.model.asset.AssetPlatform
import kotlinx.coroutines.flow.Flow
import com.easy.wallet.database.AssetPlatform as Entity

interface AssetPlatformDao {
    fun findAllStream(): Flow<List<AssetPlatform>>

    fun findByIdStream(platformId: String): Flow<AssetPlatform?>

    suspend fun insert(vararg assetPlatforms: Entity)
}
