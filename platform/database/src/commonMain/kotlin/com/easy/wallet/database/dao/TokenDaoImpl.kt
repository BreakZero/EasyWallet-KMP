package com.easy.wallet.database.dao

import com.easy.wallet.database.TokenQueries
import com.easy.wallet.model.token.Token
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

class TokenDaoImpl internal constructor(
    private val tokenQueries: TokenQueries,
    private val dispatcher: CoroutineDispatcher = Dispatchers.IO
) : TokenDao {
    override suspend fun findById(tokenId: String): Token = withContext(dispatcher) {
        val token = tokenQueries.findById(tokenId).executeAsOne()
        token.toExternalToken()
    }
}
