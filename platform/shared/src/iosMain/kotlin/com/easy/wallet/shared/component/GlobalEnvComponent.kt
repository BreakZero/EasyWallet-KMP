package com.easy.wallet.shared.component

import com.easy.wallet.shared.data.global.HDWalletInstant
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.experimental.ExperimentalObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName("GlobalEnvComponent")
class GlobalEnvComponent : KoinComponent {
    private val hdWalletInstant: HDWalletInstant by inject()

    fun loadInMemory(mnemonic: String, passphrase: String = "") {
        hdWalletInstant.loadInMemory(mnemonic, passphrase)
    }

    fun mnemonic(): String = hdWalletInstant.hdWallet()
}
