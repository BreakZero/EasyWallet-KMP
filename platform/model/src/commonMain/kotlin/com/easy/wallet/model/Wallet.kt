package com.easy.wallet.model

import kotlinx.datetime.LocalDateTime
import kotlin.native.ObjCName

@ObjCName("Wallet")
data class Wallet(
    val mnemonic: String,
    val passphrase: String,
    val isActivated: Boolean,
    val createAt: LocalDateTime
)
