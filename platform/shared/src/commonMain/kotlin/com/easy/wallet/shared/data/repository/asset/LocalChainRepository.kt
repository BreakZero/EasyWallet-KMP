package com.easy.wallet.shared.data.repository.asset

import com.easy.wallet.database.BlockChainEntity
import com.easy.wallet.database.dao.ChainDao
import com.easy.wallet.model.ChainInformation
import com.easy.wallet.model.enums.CoinVals
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class LocalChainRepository(
    private val chainDao: ChainDao
) : ChainRepository {
    override suspend fun addOne(
        name: String,
        website: String,
        explorer: String?,
        layer2Type: CoinVals.ChainLayer2Type?,
        chainId: String?
    ) {
        chainDao.addOne(name, website, explorer, layer2Type, chainId)
    }

    override suspend fun deleteById(id: Long) {
        chainDao.deleteById(id)
    }

    override fun getChainById(id: Long): Flow<ChainInformation> {
        return flow {
            val localChainEntity = chainDao.getChainById(id)
                ?: throw NoSuchElementException("couldn't found the row in table that id = $id")
            emit(localChainEntity)
        }.map(BlockChainEntity::asModel)
    }

    override fun allChains(): Flow<List<ChainInformation>> {
        return flow {
            val chains = chainDao.allChains().map(BlockChainEntity::asModel)
            emit(chains)
        }
    }
}

internal fun BlockChainEntity.asModel(): ChainInformation {
    return ChainInformation(
        id = id,
        name = name,
        website = website,
        explorer = explorer,
        layer2Type = layer2_type,
        chainId = chain_id
    )
}
