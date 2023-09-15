package com.easy.wallet.data.global

import com.trustwallet.core.HDWallet
import kotlinx.atomicfu.locks.SynchronizedObject
import kotlinx.atomicfu.locks.synchronized

class HDWalletInstant internal constructor() : SynchronizedObject() {
    private var hdWallet: HDWallet? = null

    fun loadToMemory(
        mnemonic: String,
        passphrase: String = ""
    ) {
        synchronized(this) {
            hdWallet = HDWallet(mnemonic, passphrase)
        }
    }

    /**
     * when using this function, please add check exception
     */
    fun hdWallet() = synchronized(this) {
        checkNotNull(hdWallet)
        ""
    }

    fun reset() {
        synchronized(this) {
            hdWallet = null
        }
    }
}
