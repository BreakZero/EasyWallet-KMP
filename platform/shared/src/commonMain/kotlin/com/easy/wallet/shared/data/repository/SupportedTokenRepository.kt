package com.easy.wallet.shared.data.repository

import com.easy.wallet.database.dao.BlockChainDao
import com.easy.wallet.database.dao.TokenDao
import com.easy.wallet.model.token.Token
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.experimental.ExperimentalObjCRefinement
import kotlin.native.HiddenFromObjC

@OptIn(ExperimentalObjCRefinement::class)
class SupportedTokenRepository internal constructor(
    private val blockChainDao: BlockChainDao,
    private val tokenDao: TokenDao
) {
    @HiddenFromObjC
    fun allSupportedTokenStream(): Flow<List<Token>> {
        return flow {
            emit(allSupportedToken())
        }
    }

    @HiddenFromObjC
    suspend fun allSupportedToken(): List<Token> {
        return blockChainDao.allSupportedToken()
    }

    @HiddenFromObjC
    fun findTokenByIdFlow(tokenId: String): Flow<Token> {
        return flow { emit(tokenDao.findById(tokenId)) }
    }
}
