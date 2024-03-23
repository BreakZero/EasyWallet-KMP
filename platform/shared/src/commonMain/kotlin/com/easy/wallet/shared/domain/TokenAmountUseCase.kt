package com.easy.wallet.shared.domain

import com.easy.wallet.model.TokenInformation
import com.easy.wallet.model.Wallet
import com.easy.wallet.shared.data.repository.TokenRepository
import com.trustwallet.core.HDWallet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TokenAmountUseCase internal constructor(
    private val ethereumRepository: TokenRepository,
    private val bitcoinRepository: TokenRepository
) {
    operator fun invoke(
        wallet: Wallet,
        tokenInformation: TokenInformation
    ): Flow<String> {
        val hdWallet = HDWallet(wallet.mnemonic, wallet.passphrase)
        return flow { emit("999") }
    }
}
