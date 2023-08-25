package com.easy.wallet.model

import kotlinx.datetime.LocalDateTime

data class Wallet(
    val mnemonic: String,
    val passphrase: String,
    val isActivated: Boolean,
    val createAt: LocalDateTime
)
