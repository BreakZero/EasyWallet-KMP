package com.easy.wallet.data.usecase

import com.easy.wallet.data.repository.BitcoinRepository
import com.easy.wallet.data.repository.SupportedTokenRepository
import com.easy.wallet.data.repository.TokenRepository
import com.easy.wallet.model.token.Token
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine

class DashboardUseCase internal constructor(
    private val supportedTokenRepository: SupportedTokenRepository,
    private val ethereumRepository: TokenRepository,
    private val bitcoinRepository: TokenRepository
) {
    operator fun invoke(): Flow<List<Token>> {
        return combine(
            supportedTokenRepository.allSupportedTokens(),
            ethereumRepository.loadBalance(),
            bitcoinRepository.loadBalance()
        ) { tokens, ethBalances, btcBalances ->
            println("===== $ethBalances, $btcBalances")
            tokens
        }
    }
}
