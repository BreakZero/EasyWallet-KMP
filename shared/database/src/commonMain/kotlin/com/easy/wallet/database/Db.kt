package com.easy.wallet.database

import app.cash.sqldelight.db.SqlDriver
import com.easy.wallet.database.adapters.DateTimeAdapter
import comeasywalletdatabase.HdWalletEntity

fun createQueryWrapper(driver: SqlDriver): WalletDatabase {
    return WalletDatabase(
        driver = driver,
        hdWalletEntityAdapter = HdWalletEntity.Adapter(createAtAdapter = DateTimeAdapter())
    )
}
