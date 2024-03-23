package com.easy.wallet.shared.domain

import com.easy.wallet.model.TokenInformation
import com.easy.wallet.model.Wallet
import com.easy.wallet.shared.data.multiwallet.MultiWalletRepository
import com.easy.wallet.shared.data.repository.TokenRepository
import com.trustwallet.core.HDWallet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlin.random.Random

class TokenAmountUseCase internal constructor(
    private val walletRepository: MultiWalletRepository,
    private val ethereumRepository: TokenRepository,
    private val bitcoinRepository: TokenRepository
) {
    operator fun invoke(
        tokenInformation: TokenInformation
    ): Flow<String> {
        return walletRepository.forActivatedOne().map { wallet ->
            val hdWallet = wallet?.let { HDWallet(it.mnemonic, it.passphrase) }
                ?: throw IllegalArgumentException("No wallet had been set yet.")
            "999"
        }
    }
}
