package com.easy.wallet.shared.data.repository.asset

import com.easy.wallet.model.ChainInformation
import kotlinx.coroutines.flow.Flow

interface ChainManageRepository {
    suspend fun addOne(
        name: String,
        website: String,
        explorer: String?,
        rpcUrl: String,
        chainId: String?
    )

    suspend fun deleteById(id: Long)

    fun getChainById(id: Long): Flow<ChainInformation>

    fun allChains(): Flow<List<ChainInformation>>
}
