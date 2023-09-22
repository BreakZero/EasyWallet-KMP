package com.easy.wallet.shared.model

import com.easy.wallet.model.enums.CoinVals
import com.ionspin.kotlin.bignum.decimal.BigDecimal
import com.ionspin.kotlin.bignum.integer.BigInteger

data class Balance(
    val type: CoinVals.CoinType,
    val address: String,
    val decimal: Int,
    val balance: BigInteger
) {
    companion object {
        val ZERO = Balance(
            type = CoinVals.CoinType.COIN,
            address = "",
            decimal = 0,
            balance = BigInteger.ZERO,
        )
    }

    fun approximate(scale: Long = 8): String {
        return if (balance == BigInteger.ZERO) {
            "0.00"
        } else {
            BigDecimal.fromBigInteger(balance)
                .moveDecimalPoint(-decimal).scale(scale)
                .toPlainString()
        }
    }
}
