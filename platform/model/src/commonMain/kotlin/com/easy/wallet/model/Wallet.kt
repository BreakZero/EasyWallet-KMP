package com.easy.wallet.model

import kotlinx.datetime.LocalDateTime
import kotlin.experimental.ExperimentalObjCName
import kotlin.native.ObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName("Wallet")
data class Wallet(
    val mnemonic: String,
    val passphrase: String,
    val isActivated: Boolean,
    val createAt: LocalDateTime
)
