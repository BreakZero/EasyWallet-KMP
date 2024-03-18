package com.easy.wallet.database.dao

import com.easy.wallet.database.TokenEntityQueries
import com.easy.wallet.model.TokenInformation
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class LocalTokenDaoImpl internal constructor(
    private val tokenEntityQueries: TokenEntityQueries,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : LocalTokenDao {
    override suspend fun addOne(
        id: String,
        chainId: Long,
        name: String,
        symbol: String,
        decimal: Int,
        contractAddress: String?,
        iconUri: String,
        tags: String,
        isActive: Boolean
    ) = withContext(dispatcher) {
        tokenEntityQueries.addOne(
            id,
            chainId,
            name,
            symbol,
            decimal,
            contractAddress,
            iconUri,
            isActive,
            tags
        )
    }

    override suspend fun allTokens(): List<TokenInformation> = withContext(dispatcher) {
        val tokens = tokenEntityQueries.getAll().executeAsList()
        tokens.map {
            TokenInformation(
                id = it.id,
                chainName = it.chain_name.orEmpty(),
                name = it.name,
                symbol = it.symbol,
                decimals = it.decimals ?: 8,
                contract = it.contract_address,
                iconUri = it.icon_uri.orEmpty(),
                isActive = it.is_active ?: false
            )
        }
    }

    override suspend fun findById(id: String): TokenInformation {
        val entity = tokenEntityQueries.findById(id).executeAsOneOrNull()
            ?: throw NoSuchElementException("could not find the token id: $id")
        return TokenInformation(
            id = entity.id,
            chainName = entity.chain_name.orEmpty(),
            name = entity.name,
            symbol = entity.symbol,
            decimals = entity.decimals ?: 8,
            contract = entity.contract_address,
            iconUri = entity.icon_uri.orEmpty(),
            isActive = entity.is_active ?: false
        )
    }
}
