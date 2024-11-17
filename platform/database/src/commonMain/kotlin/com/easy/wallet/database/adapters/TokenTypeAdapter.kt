package com.easy.wallet.database.adapters

import app.cash.sqldelight.ColumnAdapter
import com.easy.wallet.model.enums.CoinVals

class TokenTypeAdapter : ColumnAdapter<CoinVals.CoinType, String> {
  override fun encode(value: CoinVals.CoinType): String = value.name

  override fun decode(databaseValue: String): CoinVals.CoinType = CoinVals.CoinType.valueOf(databaseValue)
}
