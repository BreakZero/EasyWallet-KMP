package com.easy.wallet.domain.hdwallet

import com.trustwallet.core.HDWallet

class CreateWalletUseCase {
    operator fun invoke(strength: Int = 128): String {
        val hdWallet = HDWallet(strength, "")
        return hdWallet.mnemonic
    }
}