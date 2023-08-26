package com.easy.wallet.database.platform

import android.content.Context
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.easy.wallet.database.WalletDatabase
import net.sqlcipher.database.SQLiteDatabase
import net.sqlcipher.database.SupportFactory

actual class DatabaseDriverFactory(
    private val context: Context
) {
    actual fun createDriver(passphrase: String): SqlDriver {
        val supportFactory = SupportFactory(SQLiteDatabase.getBytes(passphrase.toCharArray()))
        return AndroidSqliteDriver(WalletDatabase.Schema, context, name = "e_wallet.db", factory = supportFactory)
    }
}