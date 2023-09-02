package com.easy.wallet.database

import app.cash.sqldelight.db.SqlDriver
import com.easy.wallet.database.adapters.DateTimeAdapter
import com.easy.wallet.database.adapters.DecimalsAdapter
import com.easy.wallet.database.adapters.NetworkAdapter
import com.easy.wallet.database.adapters.TokenTypeAdapter
import com.easy.wallet.database.platform.DatabaseDriverFactory
import com.easy.wallet.datastore.DatabaseKeyStorage
import comeasywalletdatabase.HdWalletEntity
import migrations.Contract
import migrations.Token

class SharedDatabase(
    databaseKeyStorage: DatabaseKeyStorage,
    factory: DatabaseDriverFactory
) {
    val database = factory.createDriver(databaseKeyStorage.databaseKey()).createDatabase()
    private fun SqlDriver.createDatabase(): WalletDatabase {
        return WalletDatabase(
            driver = this,
            hdWalletEntityAdapter = HdWalletEntity.Adapter(createAtAdapter = DateTimeAdapter()),
            tokenAdapter = Token.Adapter(
                decimalsAdapter = DecimalsAdapter(),
                typeAdapter = TokenTypeAdapter()
            ),
            contractAdapter = Contract.Adapter(networkAdapter = NetworkAdapter())
        )
    }
}