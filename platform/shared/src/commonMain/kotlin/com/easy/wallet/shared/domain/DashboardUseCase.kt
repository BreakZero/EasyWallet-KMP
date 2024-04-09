package com.easy.wallet.shared.domain

import co.touchlab.kermit.Logger
import com.easy.wallet.core.commom.Constants
import com.easy.wallet.model.Wallet
import com.easy.wallet.shared.data.repository.SupportedTokenRepository
import com.easy.wallet.shared.data.repository.TokenRepository
import com.easy.wallet.shared.model.Balance
import com.easy.wallet.shared.model.TokenUiModel
import com.ionspin.kotlin.bignum.integer.toBigInteger
import com.trustwallet.core.CoinType
import com.trustwallet.core.HDWallet
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart

class DashboardUseCase internal constructor(
    private val supportedTokenRepository: SupportedTokenRepository,
    private val ethereumRepository: TokenRepository,
    private val bitcoinRepository: TokenRepository
) {
    operator fun invoke(wallet: Wallet): Flow<List<TokenUiModel>> {
        val hdWallet = HDWallet(wallet.mnemonic, wallet.passphrase)
        return supportedTokenRepository.allSupportedTokenStream().map { tokens ->
            val ethereumAddress = hdWallet.getAddressForCoin(CoinType.Ethereum)
            val ethereumTokens = tokens.filter { it.chainName == Constants.ETH_CHAIN_NAME }
            ethereumTokens.map { token ->
                coroutineScope {
                    async {
                        val balance = try {
                            ethereumRepository.loadBalance(ethereumAddress, token.contract)
                        } catch (e: Exception) {
                            Logger.w("DashboardUseCase: ") {
                                e.message ?: "unknown error"
                            }
                            "0.00"
                        }

                        TokenUiModel(
                            token, Balance(
                                contract = token.contract,
                                decimals = token.decimals,
                                balance = balance.toBigInteger(),
                            )
                        )
                    }
                }
            }.awaitAll()
        }.onStart {
            val emptyBalance = supportedTokenRepository.allSupportedToken().map {
                TokenUiModel(it, Balance.ZERO)
            }
            emit(emptyBalance)
        }
    }
}
