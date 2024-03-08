package com.easy.wallet.database.dao

import com.easy.wallet.database.BlockChain
import com.easy.wallet.database.ChainEntity
import com.easy.wallet.model.token.Token

interface BlockChainDao {
    suspend fun insert(blockChain: BlockChain)

    suspend fun allSupportedToken(): List<Token>

    suspend fun allSupportedTokenByChain(chainName: String)
}


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
