package com.easy.wallet.shared.data.repository.asset

import com.easy.wallet.database.CoinEntity
import com.easy.wallet.database.dao.AssetPlatformDao
import com.easy.wallet.database.dao.CoinDao
import com.easy.wallet.model.asset.AssetPlatform
import com.easy.wallet.model.asset.BasicCoin
import kotlinx.coroutines.flow.Flow
import kotlin.math.log

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

    override suspend fun findCoinsById(coinId: String): List<BasicCoin> {
        return coinDao.findCoinsById(coinId)
    }

    override suspend fun insertCoin(
        id: String,
        platformId: String,
        symbol: String,
        name: String,
        logoUri: String,
        contract: String?,
        decimalPlace: Int
    ) {
        val coinEntity = CoinEntity(
            id = id,
            platform_id = platformId,
            symbol = symbol,
            name = name,
            logo_uri = logoUri,
            contract = contract,
            decimal_place = decimalPlace,
            is_active = true
        )
        coinDao.insert(coinEntity)
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
