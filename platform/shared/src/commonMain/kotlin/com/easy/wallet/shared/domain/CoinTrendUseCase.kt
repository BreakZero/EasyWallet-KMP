package com.easy.wallet.shared.domain

import com.easy.wallet.shared.data.multiwallet.MultiWalletRepository
import com.easy.wallet.shared.data.repository.TokenRepository
import com.trustwallet.core.HDWallet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.random.Random

class CoinTrendUseCase internal constructor(
    private val walletRepository: MultiWalletRepository,
    private val ethereumRepository: TokenRepository,
    private val bitcoinRepository: TokenRepository,
) {
    operator fun invoke(tokenId: String): Flow<List<String>> {
        // check which repository should be used

        // mock result for right now
        return walletRepository.forActivatedOne().map { wallet ->
            val hdWallet = wallet?.let { HDWallet(it.mnemonic, it.passphrase) }
                ?: throw IllegalArgumentException("No wallet had been set yet.")
            List(10) { Random.nextInt(12).toString() }
        }
    }
}
