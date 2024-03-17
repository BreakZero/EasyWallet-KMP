package com.easy.wallet.database.dao

import com.easy.wallet.model.TokenInformation
import com.easy.wallet.model.token.Token

interface TokenDao {
    suspend fun findById(tokenId: String): Token?
}

interface LocalTokenDao {
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

    suspend fun findById(id: String): TokenInformation

    suspend fun allTokens(): List<TokenInformation>
}
