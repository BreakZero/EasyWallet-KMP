package com.easy.wallet.data.token

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.easy.wallet.data.token.mapper.toExternalToken
import com.easy.wallet.database.SharedDatabase
import com.easy.wallet.model.token.Token
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import com.easy.wallet.database.Token as LocalToken

internal class TokenLocalDatasource(
    sharedDatabase: SharedDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private val blockChainQueries = sharedDatabase.database.blockChainQueries

    fun tokensStream(): Flow<List<Token>> {
        return blockChainQueries.transactionWithResult {
            val chainTokenFlow = blockChainQueries.selectByName(chainName = "Ethereum").asFlow()
                .mapToList(dispatcher)
            val tokensFlow = blockChainQueries.selectWithTokens(chainName = "Ethereum").asFlow()
                .mapToList(dispatcher)
            combine(chainTokenFlow, tokensFlow) { _chainToken, _tokens ->
                val chainToken = _chainToken.map { it.toExternalToken() }
                val tokens = _tokens.map(LocalToken::toExternalToken)
                chainToken + tokens
            }
        }
    }
}
