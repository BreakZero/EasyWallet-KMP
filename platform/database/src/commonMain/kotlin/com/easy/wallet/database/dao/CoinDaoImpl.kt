package com.easy.wallet.database.dao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.easy.wallet.database.CoinEntity
import com.easy.wallet.database.CoinEntityQueries
import com.easy.wallet.database.FindAllCoins
import com.easy.wallet.database.FindAllCoinsInPlatform
import com.easy.wallet.database.model.EvmNetworkInformation
import com.easy.wallet.database.model.asPublish
import com.easy.wallet.model.asset.AssetCoin
import com.easy.wallet.model.asset.AssetPlatform
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class CoinDaoImpl(
    private val queries: CoinEntityQueries,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoinDao {
    override suspend fun insert(vararg coins: CoinEntity) {
        queries.transaction {
            coins.onEach(queries::insertFullObject)
        }
    }

    override suspend fun findAllStream(): Flow<List<AssetCoin>> {
        return queries.findAllCoins().asFlow().mapToList(dispatcher)
            .map { it.map(FindAllCoins::asPublish) }
    }

    override suspend fun findAllByPlatformStream(platformId: String): Flow<List<AssetCoin>> {
        return queries.findAllCoinsInPlatform(platformId).asFlow().mapToList(dispatcher)
            .map { it.map(FindAllCoinsInPlatform::asPublish) }
    }
}

private fun FindAllCoins.asPublish(): AssetCoin {
    return AssetCoin(
        id = id,
        symbol = symbol,
        name = name,
        logoURI = logo_uri,
        contract = contract,
        platform = AssetPlatform(
            id = id_!!,
            shortName = short_name!!,
            chainIdentifier = chain_identifier,
            network = EvmNetworkInformation.decodeFromString(evm_network_info)?.asPublish()
        )
    )
}

private fun FindAllCoinsInPlatform.asPublish(): AssetCoin {
    return AssetCoin(
        id = id,
        symbol = symbol,
        name = name,
        logoURI = logo_uri,
        contract = contract,
        platform = AssetPlatform(
            id = id_!!,
            shortName = short_name!!,
            chainIdentifier = chain_identifier,
            network = EvmNetworkInformation.decodeFromString(evm_network_info)?.asPublish()
        )
    )
}
