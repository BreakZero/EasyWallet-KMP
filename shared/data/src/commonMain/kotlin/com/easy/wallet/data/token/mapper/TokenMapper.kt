package com.easy.wallet.data.token.mapper

import com.easy.wallet.database.BlockChain
import com.easy.wallet.model.token.Token
import com.easy.wallet.database.Token as LocalToken

internal fun BlockChain.toExternalToken(): Token {
    return Token(
        id = name,
        name = name,
        symbol = symbol,
        decimals = decimals ?: 0,
        type = type,
        address = "",
        logoURI = logo_uri
    )
}

internal fun LocalToken.toExternalToken(): Token {
    return Token(
        id = id,
        name = name,
        symbol = symbol,
        decimals = decimals,
        type = type,
        logoURI = logo_uri,
        address = address
    )
}