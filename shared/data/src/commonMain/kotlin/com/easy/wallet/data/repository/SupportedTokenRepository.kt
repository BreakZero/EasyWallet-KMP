package com.easy.wallet.data.repository

import com.easy.wallet.database.dao.BlockChainDao
import com.easy.wallet.model.token.Token
import kotlinx.coroutines.flow.Flow
import kotlin.experimental.ExperimentalObjCRefinement
import kotlin.native.HiddenFromObjC

@OptIn(ExperimentalObjCRefinement::class)
class SupportedTokenRepository internal constructor(
    private val blockChainDao: BlockChainDao
) {
    @HiddenFromObjC
    fun allSupportedTokens(): Flow<List<Token>> {
        return blockChainDao.allTokenStream()
    }
}

