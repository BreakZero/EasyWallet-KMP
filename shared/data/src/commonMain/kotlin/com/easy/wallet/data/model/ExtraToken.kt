package com.easy.wallet.data.model

import com.easy.wallet.model.token.Token

data class ExtraToken(
    val token: Token,
    val balance: Balance
)
