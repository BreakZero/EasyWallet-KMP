package com.easy.wallet.shared.data.repository.asset

import com.easy.wallet.database.ChainEntity
import com.easy.wallet.database.dao.ChainDao
import com.easy.wallet.model.ChainInformation
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
        rpcUrl: String,
        chainId: String?
    ) {
        chainDao.addOne(name, website, explorer, rpcUrl, chainId)
    }

    override suspend fun deleteById(id: Long) {
        chainDao.deleteById(id)
    }

    override fun getChainById(id: Long): Flow<ChainInformation> {
        return flow {
            val localChainEntity = chainDao.getChainById(id)
                ?: throw NoSuchElementException("couldn't found the row in table that id = $id")
            emit(localChainEntity)
        }.map(ChainEntity::asModel)
    }

    override fun allChains(): Flow<List<ChainInformation>> {
        return flow {
            val chains = chainDao.allChains().map(ChainEntity::asModel)
            emit(chains)
        }
    }
}

internal fun ChainEntity.asModel(): ChainInformation {
    return ChainInformation(
        id = id,
        name = name,
        website = website,
        explorer = explorer,
        rpcUrl = rpc_url,
        chainId = chain_id
    )
}
