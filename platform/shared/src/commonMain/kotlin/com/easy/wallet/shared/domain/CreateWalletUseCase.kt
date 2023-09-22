package com.easy.wallet.shared.domain

import com.trustwallet.core.HDWallet

class CreateWalletUseCase {
    operator fun invoke(strength: Int = 128): String {
        val hdWallet = HDWallet(strength, "")
        return hdWallet.mnemonic
    }
}
