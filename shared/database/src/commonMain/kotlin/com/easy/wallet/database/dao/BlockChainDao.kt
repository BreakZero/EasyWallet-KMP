package com.easy.wallet.database.dao

import com.easy.wallet.database.BlockChain
import com.easy.wallet.model.token.Token
import kotlinx.coroutines.flow.Flow

interface BlockChainDao {
    suspend fun insert(blockChain: BlockChain)

    suspend fun allSupportedToken(): List<Token>
    fun allTokenStream(): Flow<List<Token>>
}
