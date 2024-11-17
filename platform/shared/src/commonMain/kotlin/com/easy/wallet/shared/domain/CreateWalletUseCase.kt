package com.easy.wallet.shared.domain

import com.trustwallet.core.HDWallet

class CreateWalletUseCase internal constructor() {
  operator fun invoke(strength: Int = 128, passphrase: String = ""): String {
    val hdWallet = HDWallet(strength, passphrase)
    return hdWallet.mnemonic
  }
}
