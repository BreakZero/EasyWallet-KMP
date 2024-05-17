package com.easy.wallet.database.dao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.easy.wallet.database.CoinEntity
import com.easy.wallet.database.CoinEntityQueries
import com.easy.wallet.database.FindAllCoins
import com.easy.wallet.database.FindAllCoinsInPlatform
import com.easy.wallet.database.FindCoinById
import com.easy.wallet.database.model.EvmNetworkInformation
import com.easy.wallet.database.model.asPublish
import com.easy.wallet.model.asset.AssetPlatform
import com.easy.wallet.model.asset.BasicCoin
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext

internal class CoinDaoImpl(
    private val queries: CoinEntityQueries,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : CoinDao {
    override suspend fun insert(vararg coins: CoinEntity) {
        queries.transaction {
            coins.onEach {
                val isExisted = queries.findCoinById(it.id).executeAsOneOrNull() != null
                if (isExisted) {
                    return@onEach
                } else {
                    queries.insertFullObject(it)
                }
            }
        }
    }

    override suspend fun findCoinById(id: String): BasicCoin? {
        val entity = queries.findCoinById(id).executeAsOneOrNull()
        return entity?.asPublish()
    }

    override suspend fun findAllCoin(): List<BasicCoin> {
        return queries.findAllCoins().executeAsList().map(FindAllCoins::asPublish)
    }

    override fun findAllStream(): Flow<List<BasicCoin>> {
        return queries.findAllCoins().asFlow().mapToList(dispatcher)
            .map { it.map(FindAllCoins::asPublish) }
    }

    override fun findAllByPlatformStream(platformId: String): Flow<List<BasicCoin>> {
        return queries.findAllCoinsInPlatform(platformId).asFlow().mapToList(dispatcher)
            .map { it.map(FindAllCoinsInPlatform::asPublish) }
    }

    override fun allCoinStream(): Flow<List<BasicCoin>> {
        return queries.findAllCoins().asFlow().mapToList(dispatcher)
            .map { it.map(FindAllCoins::asPublish) }
    }

    override suspend fun findCoinsById(id: String): List<BasicCoin> = withContext(dispatcher) {
        val entities = queries.findCoinById(id).executeAsList()
        entities.map(FindCoinById::asPublish)
    }

    override fun findCoinsByIdStream(id: String): Flow<List<BasicCoin>> {
        return queries.findCoinById(id).asFlow().mapToList(dispatcher).map {
            it.map(FindCoinById::asPublish)
        }
    }
}

private fun FindCoinById.asPublish(): BasicCoin {
    return BasicCoin(
        id = id,
        symbol = symbol,
        name = name,
        decimalPlace = decimal_place,
        logoURI = logo_uri,
        contract = contract,
        platform = AssetPlatform(
            id = platform_id,
            shortName = short_name,
            chainIdentifier = chain_identifier,
            network = EvmNetworkInformation.decodeFromString(evm_network_info)
                ?.asPublish()
        )
    )
}

private fun FindAllCoins.asPublish(): BasicCoin {
    return BasicCoin(
        id = id,
        symbol = symbol,
        decimalPlace = decimal_place,
        name = name,
        logoURI = logo_uri,
        contract = contract,
        platform = AssetPlatform(
            id = id_,
            shortName = short_name,
            chainIdentifier = chain_identifier,
            network = EvmNetworkInformation.decodeFromString(evm_network_info)?.asPublish()
        )
    )
}

private fun FindAllCoinsInPlatform.asPublish(): BasicCoin {
    return BasicCoin(
        id = id,
        symbol = symbol,
        decimalPlace = decimal_place,
        name = name,
        logoURI = logo_uri,
        contract = contract,
        platform = AssetPlatform(
            id = platform_id,
            shortName = short_name,
            chainIdentifier = chain_identifier,
            network = EvmNetworkInformation.decodeFromString(evm_network_info)?.asPublish()
        )
    )
}
