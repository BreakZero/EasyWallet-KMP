package com.easy.wallet.shared.domain

import com.easy.wallet.core.commom.Constants
import com.easy.wallet.shared.data.multiwallet.MultiWalletRepository
import com.easy.wallet.shared.data.repository.SupportedTokenRepository
import com.easy.wallet.shared.data.repository.TokenRepository
import com.ionspin.kotlin.bignum.decimal.toBigDecimal
import com.trustwallet.core.CoinType
import com.trustwallet.core.HDWallet
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class TokenAmountUseCase internal constructor(
    private val walletRepository: MultiWalletRepository,
    private val supportedTokenRepository: SupportedTokenRepository,
    private val ethereumRepository: TokenRepository,
    private val bitcoinRepository: TokenRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Throws(NoSuchElementException::class)
    operator fun invoke(tokenId: String): Flow<String> {
        return combine(
            supportedTokenRepository.findTokenByIdFlow(tokenId),
            walletRepository.forActivatedOne()
        ) { token, wallet ->
            val hdWallet = wallet?.let { HDWallet(it.mnemonic, it.passphrase) }
                ?: throw NoSuchElementException("No wallet had been set yet.")
            val foundToken = token ?: throw NoSuchElementException("No token found, id is: $tokenId")
            Pair(hdWallet, foundToken)
        }.flatMapConcat { (hdWallet, token) ->
            when (token.chainName) {
                Constants.ETH_CHAIN_NAME -> {
                    val address = hdWallet.getAddressForCoin(CoinType.Ethereum)
                    if (token.contract.isNullOrBlank()) {
                        ethereumRepository.loadBalance(address).map {
                            val balance = it.toBigDecimal().moveDecimalPoint(-token.decimals)
                                .toPlainString()
                            "$balance ${token.symbol}"
                        }
                    } else {
                        flow { emit("000 ${token.symbol}") }
                    }
                }
                else -> flow { emit("000 ${token.symbol}") }
            }
        }
    }
}
