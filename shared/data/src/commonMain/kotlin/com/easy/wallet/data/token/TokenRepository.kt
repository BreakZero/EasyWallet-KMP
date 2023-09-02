package com.easy.wallet.data.token

class TokenRepository internal constructor(
    private val tokenLocalDatasource: TokenLocalDatasource,
    private val tokenRemoteDatasource: TokenRemoteDatasource
) {
    suspend fun loadTokens() {
        tokenLocalDatasource.loadTokens()
    }
}