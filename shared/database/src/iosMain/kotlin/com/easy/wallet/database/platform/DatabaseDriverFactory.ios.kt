package com.easy.wallet.database.platform

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import co.touchlab.sqliter.DatabaseConfiguration
import com.easy.wallet.database.Schema
import platform.Foundation.NSUUID

actual class DatabaseDriverFactory {

    actual fun createDriver(passphraseCheck: (String) -> String): SqlDriver {
        val uuid = NSUUID.UUID().UUIDString
        val key = passphraseCheck(uuid).ifBlank { uuid }

        val schema = Schema
        val databaseConfiguration = DatabaseConfiguration(
            create = {},
            name = "e_wallet.db",
            version = schema.version.toInt(),
            encryptionConfig = DatabaseConfiguration.Encryption(key = key),
        )
        return NativeSqliteDriver(schema, name = "e_wallet.db")
    }
}