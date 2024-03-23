package com.easy.wallet.shared.domain

import com.easy.wallet.model.TokenInformation
import com.easy.wallet.model.Wallet
import com.easy.wallet.shared.data.repository.TokenRepository
import com.trustwallet.core.HDWallet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.random.Random

class CoinTrendUseCase internal constructor(
    private val ethereumRepository: TokenRepository,
    private val bitcoinRepository: TokenRepository,
) {
    operator fun invoke(
        wallet: Wallet,
        tokenInformation: TokenInformation
    ): Flow<List<String>> {
        val hdWallet = HDWallet(wallet.mnemonic, wallet.passphrase)
        // check which repository should be used

        // mock result for right now
        return flow { emit(List(10) { Random.nextInt(12).toString() }) }
    }
}
