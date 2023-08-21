package com.easy.wallet.database

import app.cash.sqldelight.db.SqlDriver

fun createQueryWrapper(driver: SqlDriver): WalletDatabase {
    return WalletDatabase(driver = driver)
}
