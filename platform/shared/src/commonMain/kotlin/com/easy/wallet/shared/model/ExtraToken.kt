package com.easy.wallet.shared.model

import com.easy.wallet.model.TokenInformation

data class ExtraToken(
    val token: TokenInformation,
    val balance: Balance
)
