package com.easy.wallet.database.platform

import android.content.Context
import androidx.sqlite.db.SupportSQLiteDatabase
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.easy.wallet.database.Schema
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory
import java.util.UUID

actual class DatabaseDriverFactory(
    private val context: Context
) {
    actual fun createDriver(passphraseCheck: (String) -> String): SqlDriver {
        val uuid = UUID.randomUUID().toString()
        val key = passphraseCheck(uuid).ifBlank { uuid }
        val supportFactory = SupportFactory(SQLiteDatabase.getBytes(key.toCharArray()))
        return AndroidSqliteDriver(Schema, context, name = "e_wallet.db", factory = supportFactory,
            callback = object : AndroidSqliteDriver.Callback(Schema) {
                override fun onOpen(db: SupportSQLiteDatabase) {
                    db.setForeignKeyConstraintsEnabled(true)
                }
            })
    }
}