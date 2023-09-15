package com.easy.wallet.data.token

import com.easy.wallet.model.token.Token
import kotlinx.coroutines.flow.Flow
import kotlin.experimental.ExperimentalObjCRefinement
import kotlin.native.HiddenFromObjC

@OptIn(ExperimentalObjCRefinement::class)
class TokenRepository internal constructor(
    private val tokenLocalDatasource: TokenLocalDatasource,
    private val tokenRemoteDatasource: TokenRemoteDatasource
) {
    @HiddenFromObjC
    fun tokensStream(): Flow<List<Token>> {
        return tokenLocalDatasource.tokensStream()
    }
}
