package com.easy.wallet.database.dao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.easy.wallet.database.CoinEntity
import com.easy.wallet.database.CoinEntityQueries
import com.easy.wallet.database.FindAllCoins
import com.easy.wallet.database.FindAllCoinsInPlatform
import com.easy.wallet.database.model.EvmNetworkInformation
import com.easy.wallet.database.model.asPublish
import com.easy.wallet.model.asset.AssetPlatform
import com.easy.wallet.model.asset.BasicCoin
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

    override suspend fun findCoinById(id: String): BasicCoin? {
        val entity = queries.findCoinById(id).executeAsOneOrNull()
        return entity?.let {
            BasicCoin(
                id = it.id,
                symbol = it.symbol,
                name = it.name,
                decimalPlace = it.decimal_place,
                logoURI = it.logo_uri,
                contract = it.contract,
                platform = AssetPlatform(
                    id = it.id_!!,
                    shortName = it.short_name!!,
                    chainIdentifier = it.chain_identifier,
                    network = EvmNetworkInformation.decodeFromString(it.evm_network_info)
                        ?.asPublish()
                )
            )
        }
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
            id = id_!!,
            shortName = short_name!!,
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
            id = id_!!,
            shortName = short_name!!,
            chainIdentifier = chain_identifier,
            network = EvmNetworkInformation.decodeFromString(evm_network_info)?.asPublish()
        )
    )
}
