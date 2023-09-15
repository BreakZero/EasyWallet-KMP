package com.easy.wallet.database

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import com.easy.wallet.database.adapters.DateTimeAdapter
import com.easy.wallet.database.adapters.DecimalsAdapter
import com.easy.wallet.database.adapters.TokenTypeAdapter
import com.easy.wallet.database.platform.DatabaseDriverFactory
import com.easy.wallet.datastore.DatabaseKeyStorage

object Schema : SqlSchema<QueryResult.Value<Unit>> by WalletDatabase.Schema {
    override fun create(driver: SqlDriver): QueryResult.Value<Unit> {
        WalletDatabase.Schema.create(driver)
        driver.execute(
            null,
            """
            |INSERT OR REPLACE INTO BlockChain(
            |name, website, explorer, symbol, type, decimals, logo_uri, status
            |)
            |VALUES ("Ethereum", "https://ethereum.org/", "https://etherscan.io/", "ETH",
            |"COIN", 18, "https://raw.githubusercontent.com/trustwallet/assets/master/blockchains/ethereum/info/logo.png", 1)
            """.trimMargin(),
            0,
        )
        driver.execute(
            null,
            """
            |INSERT OR REPLACE INTO Token(
            |id, name, symbol, decimals, address, logo_uri, status, type, blockchain_name
            |)
            |VALUES ("c60_t0xc00e94Cb662C3520282E6f5717214004A7f26888", "Compound", "COMP", 18, "0xc00e94Cb662C3520282E6f5717214004A7f26888", "https://assets-cdn.trustwallet.com/blockchains/ethereum/assets/0xc00e94Cb662C3520282E6f5717214004A7f26888/logo.png", 1, "ERC20", "Ethereum")
            """.trimMargin(),
            0,
        )
        driver.execute(
            null,
            """
            |INSERT OR REPLACE INTO Token(
            |id, name, symbol, decimals, address, logo_uri, status, type, blockchain_name
            |)
            |VALUES ("c60_t0x1f9840a85d5aF5bf1D1762F925BDADdC4201F984", "Uniswap", "UNI", 18, "0x1f9840a85d5aF5bf1D1762F925BDADdC4201F984", "https://assets-cdn.trustwallet.com/blockchains/ethereum/assets/0x1f9840a85d5aF5bf1D1762F925BDADdC4201F984/logo.png", 1, "ERC20", "Ethereum")
            """.trimMargin(),
            0,
        )
        driver.execute(
            null,
            """
            |INSERT OR REPLACE INTO Token(
            |id, name, symbol, decimals, address, logo_uri, status, type, blockchain_name
            |)
            |VALUES ("c60_t0xdAC17F958D2ee523a2206206994597C13D831ec7", "Tether", "USDT", 18, "0xdAC17F958D2ee523a2206206994597C13D831ec7", "https://assets-cdn.trustwallet.com/blockchains/ethereum/assets/0xdAC17F958D2ee523a2206206994597C13D831ec7/logo.png", 1, "ERC20", "Ethereum")
            """.trimMargin(),
            0,
        )
        driver.execute(
            null,
            """
            |INSERT OR REPLACE INTO Token(
            |id, name, symbol, decimals, address, logo_uri, status, type, blockchain_name
            |)
            |VALUES ("c60_t0x6B175474E89094C44Da98b954EedeAC495271d0F", "Dai", "DAI", 18, "0x6B175474E89094C44Da98b954EedeAC495271d0F", "https://assets-cdn.trustwallet.com/blockchains/ethereum/assets/0x6B175474E89094C44Da98b954EedeAC495271d0F/logo.png", 1, "ERC20", "Ethereum")
            """.trimMargin(),
            0,
        )
        driver.execute(
            null,
            """
            |INSERT OR REPLACE INTO Token(
            |id, name, symbol, decimals, address, logo_uri, status, type, blockchain_name
            |)
            |VALUES ("c60_t0x6B3595068778DD592e39A122f4f5a5cF09C90fE2", "SUSHI", "SushiSwap", 18, "0x6B3595068778DD592e39A122f4f5a5cF09C90fE2", "https://assets-cdn.trustwallet.com/blockchains/ethereum/assets/0x6B3595068778DD592e39A122f4f5a5cF09C90fE2/logo.png", 1, "ERC20", "Ethereum")
            """.trimMargin(),
            0,
        )
        return QueryResult.Unit
    }
}

class SharedDatabase internal constructor(
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
            BlockChainAdapter = BlockChain.Adapter(
                typeAdapter = TokenTypeAdapter(),
                decimalsAdapter = DecimalsAdapter(),
            ),
            TokenAdapter = Token.Adapter(
                decimalsAdapter = DecimalsAdapter(),
                typeAdapter = TokenTypeAdapter(),
            )
        )
    }
}
