package com.easy.wallet.data.token.mapper

import com.easy.wallet.model.token.Token
import com.easy.wallet.database.Token as LocalToken

internal fun LocalToken.externalModel(): Token {
    return Token(
        coinId = coin_id,
        coinName = coin_name,
        symbol = symbol,
        decimals = decimals,
        type = type.name
    )
}