package com.easy.wallet.database.dao

import com.easy.wallet.database.ChainEntity

interface ChainDao {
    suspend fun addOne(
        name: String,
        website: String,
        explorer: String?,
        rpcUrl: String,
        chainId: String?
    )

    suspend fun deleteById(id: Long)

    suspend fun getChainById(id: Long): ChainEntity?

    suspend fun allChains() : List<ChainEntity>
}
