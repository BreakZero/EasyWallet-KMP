package com.easy.wallet.data.token

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.easy.wallet.data.token.mapper.externalModel
import com.easy.wallet.database.SharedDatabase
import com.easy.wallet.model.token.Token
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import com.easy.wallet.database.Token as LocalToken

internal class TokenLocalDatasource(
    sharedDatabase: SharedDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private val tokenQueries = sharedDatabase.database.tokenQueries
    suspend fun loadTokens() = withContext(dispatcher) {
        tokenQueries.findAll().executeAsList().map { it.externalModel() }
    }

    fun tokenStream(): Flow<List<Token>> {
        return tokenQueries.findAll().asFlow().mapToList(dispatcher).map {
            it.map(LocalToken::externalModel)
        }
    }
}