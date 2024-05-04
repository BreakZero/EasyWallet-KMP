package com.easy.wallet.shared

import okio.ByteString
import okio.ByteString.Companion.decodeHex

private fun String.containsHexPrefix(): Boolean {
    return this.length > 1 && this[0] == '0' && this[1] == 'x'
}

internal fun String.asHex(): ByteString {
    val cleanInput = if (containsHexPrefix()) this.substring(2) else this
    return cleanInput.let { if (it.length % 2 == 1) "0$it" else it }.decodeHex()
}

@OptIn(ExperimentalStdlibApi::class)
internal fun String.toHexByteArray(): ByteArray {
    val cleanInput = if (containsHexPrefix()) this.substring(2) else this
    return cleanInput.let { if (it.length % 2 == 1) "0$it" else it }.hexToByteArray()
}

