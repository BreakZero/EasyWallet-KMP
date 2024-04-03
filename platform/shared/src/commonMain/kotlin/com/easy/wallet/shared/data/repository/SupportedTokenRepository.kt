package com.easy.wallet.shared.data.repository

import com.easy.wallet.database.dao.ChainDao
import com.easy.wallet.database.dao.LocalTokenDao
import com.easy.wallet.model.TokenInformation
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.experimental.ExperimentalObjCRefinement
import kotlin.native.HiddenFromObjC

@OptIn(ExperimentalObjCRefinement::class)
class SupportedTokenRepository internal constructor(
    private val blockChainDao: ChainDao,
    private val tokenDao: LocalTokenDao
) {

    /**
     * Using for ios init default tokens
     * look into database module migrations - 2.sqm
     *
     * Do not call before you sure you need add mock data
     */
    @HiddenFromObjC
    suspend fun prepData() {
        blockChainDao.addOne(
            "Ethereum",
            "https://ethereum.org/en/",
            "https://etherscan.io",
            "https://eth.llamarpc.com",
            "1"
        )
        val chainId = blockChainDao.allChains().first().id
        tokenDao.addOne(
            "c60_t0xc00e94Cb662C3520282E6f5717214004A7f26888",
            chainId,
            "Compound",
            "COMP",
            18,
            "0xc00e94Cb662C3520282E6f5717214004A7f26888",
            "https://assets-cdn.trustwallet.com/blockchains/ethereum/assets/0xc00e94Cb662C3520282E6f5717214004A7f26888/logo.png",
            "ERC20",
            true
        )
        tokenDao.addOne(
            "c60_t0x6B175474E89094C44Da98b954EedeAC495271d0F",
            chainId,
            "Dai",
            "DAI",
            18,
            "0x6B175474E89094C44Da98b954EedeAC495271d0F",
            "https://assets-cdn.trustwallet.com/blockchains/ethereum/assets/0x6B175474E89094C44Da98b954EedeAC495271d0F/logo.png",
            "ERC20",
            true
        )
        tokenDao.addOne(
            "ether_coin",
            chainId,
            "Ethereum",
            "ETH",
            18,
            "",
            "https://raw.githubusercontent.com/trustwallet/assets/master/blockchains/ethereum/info/logo.png",
            "",
            true
        )
    }

    @HiddenFromObjC
    fun allSupportedTokenStream(): Flow<List<TokenInformation>> {
        return flow {
            emit(allSupportedToken())
        }
    }

    @HiddenFromObjC
    suspend fun allSupportedToken(): List<TokenInformation> {
        return tokenDao.allTokens()
    }

    @HiddenFromObjC
    fun findTokenByIdFlow(tokenId: String): Flow<TokenInformation?> {
        return flow { emit(tokenDao.findById(tokenId)) }
    }
}
