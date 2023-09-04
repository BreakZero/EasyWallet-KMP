package com.easy.wallet.data.token

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.easy.wallet.database.SharedDatabase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import com.easy.wallet.database.Token

internal class TokenLocalDatasource(
    sharedDatabase: SharedDatabase,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    private val tokenQueries = sharedDatabase.database.tokenQueries
    suspend fun loadTokens() = withContext(dispatcher) {
        tokenQueries.findAll().executeAsList()
    }

    fun tokenStream(): Flow<List<Token>> {
        return tokenQueries.findAll().asFlow().mapToList(dispatcher)
    }
}