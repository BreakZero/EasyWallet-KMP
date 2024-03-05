package com.easy.wallet.database.dao

import com.easy.wallet.database.BlockChain
import com.easy.wallet.database.BlockChainEntity
import com.easy.wallet.model.enums.CoinVals
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
        layer2Type: CoinVals.ChainLayer2Type?,
        chainId: String?
    )

    suspend fun deleteById(id: Long)

    suspend fun getChainById(id: Long): BlockChainEntity?

    suspend fun allChains() : List<BlockChainEntity>
}
