package com.easy.wallet.database.dao

import com.easy.wallet.database.BlockChainEntity
import com.easy.wallet.database.ChainEntityQueries
import com.easy.wallet.model.enums.CoinVals
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

internal class ChainDaoImpl(
    private val queries: ChainEntityQueries,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : ChainDao {

    override suspend fun addOne(
        name: String,
        website: String,
        explorer: String?,
        layer2Type: CoinVals.ChainLayer2Type?,
        chainId: String?
    ) {
        queries.transaction {
            val isExist = queries.checkByName(name).executeAsOne() > 0
            if (!isExist) {
                queries.addOne(
                    name = name,
                    website = website,
                    explorer = explorer,
                    layer2_type = layer2Type,
                    chain_id = chainId
                )
            }
        }
    }

    override suspend fun deleteById(id: Long) {
        queries.deleteById(id)
    }

    override suspend fun getChainById(id: Long): BlockChainEntity? = withContext(dispatcher) {
        queries.getChainById(id).executeAsOneOrNull()
    }

    override suspend fun allChains(): List<BlockChainEntity> = withContext(dispatcher) {
        queries.getAllChain().executeAsList()
    }

}
