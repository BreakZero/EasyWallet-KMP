package com.easy.wallet.shared.data.repository.asset

import com.easy.wallet.model.TokenInformation
import kotlinx.coroutines.flow.Flow

interface TokenManageRepository {
    suspend fun addOne(
        id: String,
        chainId: Long,
        name: String,
        symbol: String,
        decimal: Int,
        contractAddress: String?,
        iconUri: String,
        isActive: Boolean
    )

    suspend fun deleteByIds(ids: List<String>)

    fun allTokens(): Flow<List<TokenInformation>>

    fun findById(id: String): Flow<TokenInformation>
}
