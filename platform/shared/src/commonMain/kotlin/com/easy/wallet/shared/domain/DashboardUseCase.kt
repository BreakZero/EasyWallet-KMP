package com.easy.wallet.shared.domain

import com.easy.wallet.shared.data.multiwallet.MultiWalletRepository
import com.easy.wallet.shared.data.repository.SupportedTokenRepository
import com.easy.wallet.shared.data.repository.TokenRepository
import com.easy.wallet.shared.model.Balance
import com.easy.wallet.shared.model.ExtraToken
import com.trustwallet.core.CoinType
import com.trustwallet.core.HDWallet
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.onStart

@OptIn(ExperimentalCoroutinesApi::class)
class DashboardUseCase internal constructor(
    private val walletRepository: MultiWalletRepository,
    private val supportedTokenRepository: SupportedTokenRepository,
    private val ethereumRepository: TokenRepository,
    private val bitcoinRepository: TokenRepository
) {
    operator fun invoke(): Flow<List<ExtraToken>> {
        return walletRepository.forActivatedOne().filterNotNull().flatMapConcat {
            val hdWallet = HDWallet(it.mnemonic, it.passphrase)
            combine(
                supportedTokenRepository.allSupportedTokenStream(),
                ethereumRepository.dashboard(hdWallet.getAddressForCoin(CoinType.Ethereum)),
                bitcoinRepository.dashboard(hdWallet.getAddressForCoin(CoinType.Bitcoin)),
            ) { tokens, ethBalances, _ ->
                tokens.map { token ->
                    val balance = ethBalances.find {
                        token.contract.equals(it.address, ignoreCase = true)
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
}
