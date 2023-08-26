package com.easy.wallet.database.platform

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import co.touchlab.sqliter.DatabaseConfiguration
import com.easy.wallet.database.WalletDatabase

actual class DatabaseDriverFactory {

    actual fun createDriver(passphrase: String): SqlDriver {
        val schema = WalletDatabase.Schema
        return NativeSqliteDriver(schema, name = "e_wallet.db",
            onConfiguration = {
                DatabaseConfiguration(
                    create = {},
                    name = "",
                    version = schema.version.toInt(),
                    encryptionConfig = DatabaseConfiguration.Encryption(key = passphrase)
                )
            })
    }
}