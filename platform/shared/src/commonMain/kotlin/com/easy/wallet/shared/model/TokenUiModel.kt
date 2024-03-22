package com.easy.wallet.shared.model

import com.easy.wallet.model.TokenInformation

data class TokenUiModel(
    val token: TokenInformation,
    val balance: Balance
)
