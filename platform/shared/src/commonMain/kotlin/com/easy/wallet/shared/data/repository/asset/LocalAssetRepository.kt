package com.easy.wallet.shared.data.repository.asset

import com.easy.wallet.database.dao.AssetPlatformDao
import com.easy.wallet.database.dao.CoinDao
import com.easy.wallet.model.asset.AssetPlatform
import com.easy.wallet.model.asset.BasicCoin
import kotlinx.coroutines.flow.Flow

class LocalAssetRepository internal constructor(
    private val coinDao: CoinDao,
    private val platformDao: AssetPlatformDao
): CoinRepository, PlatformRepository {
    override fun findAllCoinStream(): Flow<List<BasicCoin>> {
        return coinDao.findAllStream()
    }

    override suspend fun findAllCoin(): List<BasicCoin> {
        return coinDao.findAllCoin()
    }

    override suspend fun findCoinById(coinId: String): BasicCoin? {
        return coinDao.findCoinById(coinId)
    }

    override suspend fun insertPlatform(
        name: String,
        rpcUrl: String,
        explorer: String?,
        chainId: String?
    ) {
        /*val entity = com.easy.wallet.database.AssetPlatform(
            id = "",
            short_name = name,
        )
        platformDao.insert(entity)*/
        TODO("Not yet implemented")
    }

    override fun allPlatformStream(): Flow<List<AssetPlatform>> {
        return platformDao.findAllStream()
    }

    override fun findPlatformByIdStream(platformId: String): Flow<AssetPlatform?> {
        return platformDao.findByIdStream(platformId)
    }
}
