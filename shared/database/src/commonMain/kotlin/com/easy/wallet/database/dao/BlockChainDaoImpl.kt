package com.easy.wallet.database.dao

import com.easy.wallet.database.BlockChain
import com.easy.wallet.database.BlockChainQueries
import com.easy.wallet.model.token.Token
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext
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

    override suspend fun allSupportedToken(): List<Token> = withContext(dispatcher) {
        val chainToken = blockChainQueries.selectAllChain().executeAsList()
            .map(BlockChain::toExternalToken)
        val tokens = blockChainQueries.selectAllToken().executeAsList()
            .map(DbToken::toExternalToken)
        chainToken + tokens
    }

    override suspend fun allSupportedTokenByChain(chainName: String) {
        val chainToken = blockChainQueries.selectByName(chainName = chainName).executeAsList()
            .map(BlockChain::toExternalToken)
        val tokens = blockChainQueries.selectWithTokens(chainName = chainName).executeAsList()
            .map(DbToken::toExternalToken)
        chainToken + tokens
    }


}
