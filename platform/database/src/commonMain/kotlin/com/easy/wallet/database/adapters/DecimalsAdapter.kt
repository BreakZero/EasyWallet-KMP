package com.easy.wallet.database.adapters

import app.cash.sqldelight.ColumnAdapter

class DecimalsAdapter : ColumnAdapter<Int, Long> {
  override fun decode(databaseValue: Long): Int = databaseValue.toInt()

  override fun encode(value: Int): Long = value.toLong()
}
