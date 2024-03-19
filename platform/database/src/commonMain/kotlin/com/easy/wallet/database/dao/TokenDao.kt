package com.easy.wallet.database.dao

import com.easy.wallet.model.TokenInformation

interface LocalTokenDao {
    suspend fun addOne(
        id: String,
        chainId: Long,
        name: String,
        symbol: String,
        decimal: Int,
        contractAddress: String?,
        iconUri: String,
        tags: String,
        isActive: Boolean
    )

    suspend fun findById(id: String): TokenInformation

    suspend fun allTokens(): List<TokenInformation>

    suspend fun deleteByIds(ids: List<String>)
}
