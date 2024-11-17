package com.easy.wallet.database.adapters

import app.cash.sqldelight.ColumnAdapter
import com.easy.wallet.model.enums.CoinVals

class ChainNetworkAdapter : ColumnAdapter<CoinVals.ChainNetwork, String> {
  override fun decode(databaseValue: String): CoinVals.ChainNetwork = CoinVals.ChainNetwork.from(databaseValue)

  override fun encode(value: CoinVals.ChainNetwork): String = value.label
}
