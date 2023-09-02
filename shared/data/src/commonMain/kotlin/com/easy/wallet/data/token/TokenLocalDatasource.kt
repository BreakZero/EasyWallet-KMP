package com.easy.wallet.data.token

import com.easy.wallet.database.SharedDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

internal class TokenLocalDatasource(
    sharedDatabase: SharedDatabase
) {
    private val tokenQueries = sharedDatabase.database.tokenQueries
    suspend fun loadTokens() = withContext(Dispatchers.IO) {
        tokenQueries.findAll().executeAsList().forEach {
            println("===== lalalal $it")
        }
    }
}