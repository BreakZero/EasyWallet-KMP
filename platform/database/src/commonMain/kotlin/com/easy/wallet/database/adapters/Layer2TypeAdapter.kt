package com.easy.wallet.database.adapters

import app.cash.sqldelight.ColumnAdapter
import com.easy.wallet.model.enums.CoinVals.ChainLayer2Type

class Layer2TypeAdapter : ColumnAdapter<ChainLayer2Type, String> {
  override fun encode(value: ChainLayer2Type): String = value.name

  override fun decode(databaseValue: String): ChainLayer2Type = ChainLayer2Type.valueOf(databaseValue)
}
