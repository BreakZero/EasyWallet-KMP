package com.easy.wallet.shared.data.repository

import com.easy.wallet.database.dao.ChainDao
import com.easy.wallet.database.dao.LocalTokenDao
import com.easy.wallet.model.TokenInformation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.experimental.ExperimentalObjCRefinement
import kotlin.native.HiddenFromObjC

@OptIn(ExperimentalObjCRefinement::class)
@HiddenFromObjC
class SupportedTokenRepository internal constructor(
    private val blockChainDao: ChainDao,
    private val tokenDao: LocalTokenDao
) {
    fun allSupportedTokenStream(): Flow<List<TokenInformation>> {
        return flow {
            emit(allSupportedToken())
        }
    }

    suspend fun allSupportedToken(): List<TokenInformation> {
        return tokenDao.allTokens()
    }

    suspend fun findTokenById(tokenId: String): TokenInformation? {
        return try {
            tokenDao.findById(tokenId)
        } catch (e: Exception) {
            null
        }
    }
}
