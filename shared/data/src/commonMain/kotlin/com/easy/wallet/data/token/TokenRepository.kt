package com.easy.wallet.data.token

import com.easy.wallet.model.token.Token
import kotlinx.coroutines.flow.Flow

class TokenRepository internal constructor(
    private val tokenLocalDatasource: TokenLocalDatasource,
    private val tokenRemoteDatasource: TokenRemoteDatasource
) {
    suspend fun loadTokens(): List<Token> {
        return tokenLocalDatasource.loadTokens()
    }
    fun tokenStream(): Flow<List<Token>> {
        return tokenLocalDatasource.tokenStream()
    }
}