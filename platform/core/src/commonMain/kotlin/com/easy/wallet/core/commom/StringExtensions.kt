package com.easy.wallet.core.commom

fun String?.ifNullOrBlank(defaultValue: () -> String): String {
    return if (isNullOrBlank()) defaultValue() else this
}

private fun String.containsHexPrefix(): Boolean {
    return this.length > 1 && this[0] == '0' && this[1] == 'x'
}

fun String.cleanHexPrefix(): String {
    return if (containsHexPrefix()) this.substring(2) else this
}
