package com.easy.wallet.data.usecase

import com.easy.wallet.data.global.HDWalletInstant
import com.easy.wallet.data.model.Balance
import com.easy.wallet.data.model.ExtraToken
import com.easy.wallet.data.repository.SupportedTokenRepository
import com.easy.wallet.data.repository.TokenRepository
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
    operator fun invoke(): Flow<List<ExtraToken>> {
        val hdWallet = HDWallet(hdWalletInstant.hdWallet(), "")
        return combine(
            supportedTokenRepository.allSupportedTokens(),
            ethereumRepository.dashboard(hdWallet.getAddressForCoin(CoinType.Ethereum)),
            bitcoinRepository.dashboard("")
        ) { tokens, ethBalances, _ ->
            tokens.map { token ->
                val balance = ethBalances.find {
                    (token.type == it.type) && token.address.equals(it.address, ignoreCase = true)
                } ?: Balance.ZERO
                ExtraToken(token, balance)
            }
        }.catch {
            emit(emptyList())
        }
    }
}
