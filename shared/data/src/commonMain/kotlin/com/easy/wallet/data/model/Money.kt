package com.easy.wallet.data.model

import com.ionspin.kotlin.bignum.decimal.BigDecimal

data class Money(
    val amount: BigDecimal,
    val scale: Int,
    val unit: String
)
