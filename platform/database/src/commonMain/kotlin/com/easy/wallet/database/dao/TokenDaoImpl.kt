package com.easy.wallet.database.dao

import com.easy.wallet.database.TokenEntityQueries
import com.easy.wallet.database.TokenQueries
import com.easy.wallet.model.TokenInformation
import com.easy.wallet.model.token.Token
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.isActive
import kotlinx.coroutines.withContext

class TokenDaoImpl internal constructor(
    private val tokenQueries: TokenQueries,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : TokenDao {
    override suspend fun findById(tokenId: String): Token? = withContext(dispatcher) {
        val token = tokenQueries.findById(tokenId).executeAsOneOrNull()
        token?.toExternalToken()
    }
}

class LocalTokenDaoImpl internal constructor(
    private val tokenEntityQueries: TokenEntityQueries,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
): LocalTokenDao {
    override suspend fun addOne(
        id: String,
        chainId: Long,
        name: String,
        symbol: String,
        decimal: Int,
        contractAddress: String?,
        iconUri: String,
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
            isActive
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
                decimal = it.decimals ?: 8,
                contract = it.contract_address,
                iconUri = it.icon_uri.orEmpty(),
                isActive = it.is_active ?: false
            )
        }
    }
}
