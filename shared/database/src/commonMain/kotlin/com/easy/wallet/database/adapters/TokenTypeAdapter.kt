package com.easy.wallet.database.adapters

import app.cash.sqldelight.ColumnAdapter
import com.easy.wallet.database.TokenVals

class TokenTypeAdapter() : ColumnAdapter<TokenVals.TokenType, String> {
    override fun encode(value: TokenVals.TokenType): String {
        return value.name
    }
    override fun decode(databaseValue: String): TokenVals.TokenType {
        return TokenVals.TokenType.valueOf(databaseValue)
    }
}