package com.easy.wallet.database.platform

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.native.NativeSqliteDriver
import app.cash.sqldelight.driver.native.wrapConnection
import co.touchlab.sqliter.DatabaseConfiguration
import com.easy.wallet.database.Schema
import platform.Foundation.NSUUID

actual class DatabaseDriverFactory {

    actual fun createDriver(passphraseCheck: (String) -> String): SqlDriver {
        val uuid = NSUUID.UUID().UUIDString
        val key = passphraseCheck(uuid).ifBlank { uuid }

        val schema = Schema
        val databaseConfiguration = DatabaseConfiguration(
            create = { connection ->
                wrapConnection(connection) { schema.create(it) }
            },
            upgrade = { connection, oldVersion, newVersion ->
                wrapConnection(connection) {
                    schema.migrate(
                        it,
                        oldVersion.toLong(),
                        newVersion.toLong(),
                    )
                }
            },
            name = "e_wallet.db",
            version = schema.version.toInt(),
            encryptionConfig = DatabaseConfiguration.Encryption(key = key),
            extendedConfig = DatabaseConfiguration.Extended(foreignKeyConstraints = true)
        )
        return NativeSqliteDriver(databaseConfiguration)
    }
}
