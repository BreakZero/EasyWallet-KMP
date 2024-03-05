package com.easy.wallet.shared.data.repository.asset

import com.easy.wallet.model.ChainInformation
import com.easy.wallet.model.enums.CoinVals
import kotlinx.coroutines.flow.Flow

interface ChainRepository {
    suspend fun addOne(
        name: String,
        website: String,
        explorer: String?,
        layer2Type: CoinVals.ChainLayer2Type?,
        chainId: String?
    )

    suspend fun deleteById(id: Long)

    fun getChainById(id: Long): Flow<ChainInformation>

    fun allChains(): Flow<List<ChainInformation>>
}
