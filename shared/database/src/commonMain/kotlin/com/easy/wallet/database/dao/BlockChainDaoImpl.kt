package com.easy.wallet.database.dao

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.easy.wallet.database.BlockChain
import com.easy.wallet.database.BlockChainQueries
import com.easy.wallet.model.token.Token
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import com.easy.wallet.database.Token as DbToken

internal fun BlockChain.toExternalToken(): Token {
    return Token(
        id = name,
        name = name,
        symbol = symbol,
        decimals = decimals ?: 0,
        type = type,
        address = name.lowercase(),
        logoURI = logo_uri,
    )
}

internal fun DbToken.toExternalToken(): Token {
    return Token(
        id = id,
        name = name,
        symbol = symbol,
        decimals = decimals,
        type = type,
        logoURI = logo_uri,
        address = address,
    )
}

class BlockChainDaoImpl internal constructor(
    private val blockChainQueries: BlockChainQueries,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : BlockChainDao {
    override suspend fun insert(blockChain: BlockChain) {
        blockChainQueries.insert(blockChain)
    }

    override fun allTokenStream(): Flow<List<Token>> {
        return blockChainQueries.transactionWithResult {
            val chainTokenFlow = blockChainQueries.selectByName(chainName = "Ethereum").asFlow()
                .mapToList(dispatcher)
            val tokensFlow = blockChainQueries.selectWithTokens(chainName = "Ethereum").asFlow()
                .mapToList(dispatcher)
            combine(chainTokenFlow, tokensFlow) { _chainToken, _tokens ->
                val chainToken = _chainToken.map { it.toExternalToken() }
                val tokens = _tokens.map(DbToken::toExternalToken)
                chainToken + tokens
            }
        }
    }

    override suspend fun allSupportedToken(): List<Token> {
        val chainToken = blockChainQueries.selectByName(chainName = "Ethereum").executeAsList()
            .map(BlockChain::toExternalToken)
        val tokens = blockChainQueries.selectWithTokens(chainName = "Ethereum").executeAsList()
            .map(DbToken::toExternalToken)
        return chainToken + tokens
    }
}
