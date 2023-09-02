package com.easy.wallet.database.adapters

import app.cash.sqldelight.ColumnAdapter
import com.easy.wallet.core.commom.DateTimeDecoder
import com.easy.wallet.database.TokenVals
import kotlinx.datetime.LocalDateTime

class NetworkAdapter() : ColumnAdapter<TokenVals.Network, String> {
    override fun decode(databaseValue: String): TokenVals.Network {
        return TokenVals.Network.from(databaseValue)
    }

    override fun encode(value: TokenVals.Network): String {
        return value.label
    }
}