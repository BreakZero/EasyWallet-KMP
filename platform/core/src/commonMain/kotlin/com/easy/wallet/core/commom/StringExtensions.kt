package com.easy.wallet.core.commom

fun String?.ifNullOrBlank(defaultValue: () -> String): String = if (isNullOrBlank()) defaultValue() else this

private fun String.containsHexPrefix(): Boolean = this.length > 1 && this[0] == '0' && this[1] == 'x'

fun String.cleanHexPrefix(): String = if (containsHexPrefix()) this.substring(2) else this
