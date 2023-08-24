package com.easy.wallet.domain.hdwallet

import com.trustwallet.core.HDWallet

class HDWalletManager {
    fun create(passphrase: String = "") {
        val hdWallet = HDWallet(12, passphrase)
    }

    fun restore(mnemonic: String, passphrase: String = "") {
        val hdWallet = HDWallet(mnemonic, passphrase)
    }
}