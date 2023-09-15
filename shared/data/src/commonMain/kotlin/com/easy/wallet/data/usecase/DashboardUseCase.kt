package com.easy.wallet.data.usecase

import com.easy.wallet.data.global.HDWalletInstant
import com.easy.wallet.data.repository.SupportedTokenRepository
import com.easy.wallet.data.repository.TokenRepository
import com.easy.wallet.model.token.TokenWithBalance
import com.trustwallet.core.CoinType
import com.trustwallet.core.HDWallet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine

class DashboardUseCase internal constructor(
    private val hdWalletInstant: HDWalletInstant,
    private val supportedTokenRepository: SupportedTokenRepository,
    private val ethereumRepository: TokenRepository,
    private val bitcoinRepository: TokenRepository
) {
    operator fun invoke(): Flow<List<TokenWithBalance>> {
        val hdWallet = HDWallet(hdWalletInstant.hdWallet(), "")
        return combine(
            supportedTokenRepository.allSupportedTokens(),
            ethereumRepository.dashboard(hdWallet.getAddressForCoin(CoinType.Ethereum)),
            bitcoinRepository.dashboard("")
        ) { tokens, ethBalances, btcBalances ->
            tokens.map {
                println("local token: $it")
                val balance = ethBalances[it.address.lowercase()]
                TokenWithBalance(it, balance ?: "0.0")
            }
        }.catch {
            emit(emptyList())
        }
    }
}
