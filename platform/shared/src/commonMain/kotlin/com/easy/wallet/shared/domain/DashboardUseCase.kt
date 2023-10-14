package com.easy.wallet.shared.domain

import com.easy.wallet.shared.data.global.HDWalletInstant
import com.easy.wallet.shared.data.repository.SupportedTokenRepository
import com.easy.wallet.shared.data.repository.TokenRepository
import com.easy.wallet.shared.model.Balance
import com.easy.wallet.shared.model.ExtraToken
import com.trustwallet.core.CoinType
import com.trustwallet.core.HDWallet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart

class DashboardUseCase internal constructor(
    private val hdWalletInstant: HDWalletInstant,
    private val supportedTokenRepository: SupportedTokenRepository,
    private val ethereumRepository: TokenRepository,
    private val bitcoinRepository: TokenRepository
) {
    operator fun invoke(): Flow<List<ExtraToken>> {
        val hdWallet = HDWallet(hdWalletInstant.hdWallet(), "")
        return combine(
            supportedTokenRepository.allSupportedTokenStream(),
            ethereumRepository.dashboard(hdWallet.getAddressForCoin(CoinType.Ethereum)),
            bitcoinRepository.dashboard(hdWallet.getAddressForCoin(CoinType.Bitcoin)),
        ) { tokens, ethBalances, _ ->
            tokens.map { token ->
                val balance = ethBalances.find {
                    (token.type == it.type) && token.address.equals(it.address, ignoreCase = true)
                } ?: Balance.ZERO
                ExtraToken(token, balance)
            }
        }.onStart {
            val emptyBalance = supportedTokenRepository.allSupportedToken().map {
                ExtraToken(it, Balance.ZERO)
            }
            emit(emptyBalance)
        }
    }
}
