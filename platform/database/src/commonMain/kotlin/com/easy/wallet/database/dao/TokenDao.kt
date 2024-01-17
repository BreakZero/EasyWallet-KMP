package com.easy.wallet.database.dao

import com.easy.wallet.model.token.Token

interface TokenDao {
    suspend fun findById(tokenId: String): Token?
}
