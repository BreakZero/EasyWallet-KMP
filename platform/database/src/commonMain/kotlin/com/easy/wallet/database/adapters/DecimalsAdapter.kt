package com.easy.wallet.database.adapters

import app.cash.sqldelight.ColumnAdapter

class DecimalsAdapter : ColumnAdapter<Int, Long> {
    override fun decode(databaseValue: Long): Int {
        return databaseValue.toInt()
    }

    override fun encode(value: Int): Long {
        return value.toLong()
    }
}