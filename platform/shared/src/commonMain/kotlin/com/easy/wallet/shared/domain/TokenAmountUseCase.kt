package com.easy.wallet.shared.domain

import com.easy.wallet.core.commom.Constants
import com.easy.wallet.model.TokenInformation
import com.easy.wallet.shared.data.multiwallet.MultiWalletRepository
import com.easy.wallet.shared.data.repository.TokenRepository
import com.ionspin.kotlin.bignum.decimal.toBigDecimal
import com.trustwallet.core.CoinType
import com.trustwallet.core.HDWallet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class TokenAmountUseCase internal constructor(
    private val walletRepository: MultiWalletRepository,
    private val ethereumRepository: TokenRepository,
    private val bitcoinRepository: TokenRepository
) {
    operator fun invoke(
        tokenInformation: TokenInformation
    ): Flow<String> {
        return walletRepository.forActivatedOne().flatMapConcat { wallet ->
            val hdWallet = wallet?.let { HDWallet(it.mnemonic, it.passphrase) }
                ?: throw IllegalArgumentException("No wallet had been set yet.")
            when (tokenInformation.chainName) {
                Constants.ETH_CHAIN_NAME -> {
                    val address = hdWallet.getAddressForCoin(CoinType.Ethereum)
                    if (tokenInformation.contract.isNullOrBlank()) {
                        ethereumRepository.loadBalance(address).map {
                            it.toBigDecimal().moveDecimalPoint(-tokenInformation.decimals).toPlainString()
                        }
                    } else {
                        flow { emit("000") }
                    }
                }
                else -> flow { emit("000") }
            }
        }
    }
}
