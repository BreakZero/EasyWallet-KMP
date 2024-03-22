package com.easy.wallet.shared.domain

import com.easy.wallet.model.Wallet
import com.easy.wallet.shared.data.repository.SupportedTokenRepository
import com.easy.wallet.shared.data.repository.TokenRepository
import com.easy.wallet.shared.model.Balance
import com.easy.wallet.shared.model.TokenUiModel
import com.trustwallet.core.CoinType
import com.trustwallet.core.HDWallet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart

class DashboardUseCase internal constructor(
    private val supportedTokenRepository: SupportedTokenRepository,
    private val ethereumRepository: TokenRepository,
    private val bitcoinRepository: TokenRepository
) {
    operator fun invoke(wallet: Wallet): Flow<List<TokenUiModel>> {
        val hdWallet = HDWallet(wallet.mnemonic, wallet.passphrase)
        return combine(
            supportedTokenRepository.allSupportedTokenStream(),
            ethereumRepository.dashboard(hdWallet.getAddressForCoin(CoinType.Ethereum)),
            bitcoinRepository.dashboard(hdWallet.getAddressForCoin(CoinType.Bitcoin)),
        ) { tokens, ethBalances, _ ->
            val ethereumTokens = tokens.filter { it.chainName == "Ethereum" }
            ethereumTokens.map { token ->
                val balance = ethBalances.find {
                    token.contract.equals(it.address, ignoreCase = true)
                } ?: Balance.ZERO
                TokenUiModel(token, balance)
            }
        }.onStart {
            val emptyBalance = supportedTokenRepository.allSupportedToken().map {
                TokenUiModel(it, Balance.ZERO)
            }
            emit(emptyBalance)
        }
    }
}
