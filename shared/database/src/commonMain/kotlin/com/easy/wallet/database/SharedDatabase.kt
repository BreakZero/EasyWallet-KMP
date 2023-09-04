package com.easy.wallet.database

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import com.easy.wallet.database.adapters.DateTimeAdapter
import com.easy.wallet.database.adapters.DecimalsAdapter
import com.easy.wallet.database.adapters.NetworkAdapter
import com.easy.wallet.database.adapters.TokenTypeAdapter
import com.easy.wallet.database.platform.DatabaseDriverFactory
import com.easy.wallet.datastore.DatabaseKeyStorage

object Schema : SqlSchema<QueryResult.Value<Unit>> by WalletDatabase.Schema {
    override fun create(driver: SqlDriver): QueryResult.Value<Unit> {
        WalletDatabase.Schema.create(driver)
        driver.execute(
            null,
            "INSERT INTO contract(network, address, coin_id) VALUES (\"Ethereum\",\"0x6b175474e89094c44da98b954eedeac495271d0f\", \"dai-erc20\")",
            0
        )
        driver.execute(
            null,
            "INSERT INTO contract(network, address, coin_id) VALUES (\"Ethereum\",\"0xc00e94Cb662C3520282E6f5717214004A7f26888\", \"comp-erc20\")",
            0
        )
        driver.execute(
            null,
            "INSERT INTO contract(network, address, coin_id) VALUES (\"Ethereum\",\"0x1f9840a85d5af5bf1d1762f925bdaddc4201f984\", \"uni-erc20\")",
            0
        )
        driver.execute(
            null,
            "INSERT INTO contract(network, address, coin_id) VALUES (\"Ethereum\",\"0xa0b73e1ff0b80914ab6fe0444e65848c4c34450b\", \"cro-erc20\")",
            0
        )
        driver.execute(
            null, """
            |INSERT INTO token(coin_id, coin_name, symbol, decimals, type)
            |VALUES ("eth-main","Ethereum","ETH",18, "TOKEN")
            """.trimMargin(), 0
        )
        driver.execute(
            null, """
            |INSERT INTO token(coin_id, coin_name, symbol, decimals, type)
            |VALUES ("comp-erc20","Compound","COMP",18, "ERC20")
            """.trimMargin(), 0
        )
        driver.execute(
            null, """
            |INSERT INTO token(coin_id, coin_name, symbol, decimals, type)
            |VALUES ("uni-erc20","Uniswap","UNI",18, "ERC20")
            """.trimMargin(), 0
        )
        driver.execute(
            null, """
            |INSERT INTO token(coin_id, coin_name, symbol, decimals, type)
            |VALUES ("dai-erc20","Dai Stablecoin","DAI",18, "ERC20")
            """.trimMargin(), 0
        )
        driver.execute(
            null, """
            |INSERT INTO token(coin_id, coin_name, symbol, decimals, type)
            |VALUES ("cro-erc20","Cronos","CRO",8, "ERC20")
            """.trimMargin(), 0
        )
        return QueryResult.Unit
    }
}

class SharedDatabase(
    databaseKeyStorage: DatabaseKeyStorage,
    factory: DatabaseDriverFactory
) {
    val database: WalletDatabase = factory.createDriver {
        databaseKeyStorage.init(it)
        databaseKeyStorage.databaseKey()
    }.createDatabase()

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