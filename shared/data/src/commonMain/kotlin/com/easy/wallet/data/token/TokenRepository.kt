package com.easy.wallet.data.token

import kotlinx.coroutines.flow.Flow
import migrations.Token

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