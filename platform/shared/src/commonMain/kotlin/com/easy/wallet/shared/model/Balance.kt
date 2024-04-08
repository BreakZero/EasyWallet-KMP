package com.easy.wallet.shared.model

import com.ionspin.kotlin.bignum.decimal.BigDecimal
import com.ionspin.kotlin.bignum.integer.BigInteger

data class Balance(
    val contract: String?,
    val decimals: Int,
    val balance: BigInteger
) {
    companion object {
        val ZERO = Balance(
            contract = null,
            decimals = 0,
            balance = BigInteger.ZERO,
        )
    }

    fun approximate(scale: Long = 8): String {
        return if (balance == BigInteger.ZERO) {
            "0.00"
        } else {
            BigDecimal.fromBigInteger(balance)
                .moveDecimalPoint(-decimals).scale(scale)
                .toPlainString()
        }
    }
}
