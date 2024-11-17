package com.easy.wallet.shared.data.repository.asset

import com.easy.wallet.model.asset.AssetPlatform
import kotlinx.coroutines.flow.Flow

interface PlatformRepository {
  suspend fun insertPlatform(
    name: String,
    rpcUrl: String,
    explorer: String?,
    chainId: String?
  )

  fun allPlatformStream(): Flow<List<AssetPlatform>>

  fun findPlatformByIdStream(platformId: String): Flow<AssetPlatform?>
}
