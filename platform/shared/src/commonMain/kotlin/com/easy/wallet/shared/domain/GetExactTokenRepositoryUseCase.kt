package com.easy.wallet.shared.domain

import com.easy.wallet.core.commom.Constants
import com.easy.wallet.model.TokenBasicResult
import com.easy.wallet.shared.data.repository.NoSupportedTokenRepository
import com.easy.wallet.shared.data.repository.TokenRepository

internal class GetExactTokenRepositoryUseCase(
    private val ethereumRepository: TokenRepository,
    private val bitcoinRepository: TokenRepository,
    private val notSupportedTokenRepository: NoSupportedTokenRepository
) {
    operator fun invoke(tokenBasicResult: TokenBasicResult): TokenRepository {
        return when (tokenBasicResult.chainName) {
            Constants.ETH_CHAIN_NAME -> ethereumRepository
            else -> notSupportedTokenRepository
        }
    }
}
