package com.easy.wallet.shared.data.repository

import com.easy.wallet.database.dao.ChainDao
import com.easy.wallet.database.dao.LocalTokenDao
import com.easy.wallet.model.TokenInformation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlin.experimental.ExperimentalObjCRefinement
import kotlin.native.HiddenFromObjC

@OptIn(ExperimentalObjCRefinement::class)
class SupportedTokenRepository internal constructor(
    private val blockChainDao: ChainDao,
    private val tokenDao: LocalTokenDao
) {
    @HiddenFromObjC
    fun allSupportedTokenStream(): Flow<List<TokenInformation>> {
        return flow {
            emit(tokenDao.allTokens())
        }
    }

    @HiddenFromObjC
    suspend fun allSupportedToken(): List<TokenInformation> {
        return emptyList()
    }

    @HiddenFromObjC
    fun findTokenByIdFlow(tokenId: String): Flow<TokenInformation?> {
        return flow { emit(tokenDao.findById(tokenId)) }
    }
}
