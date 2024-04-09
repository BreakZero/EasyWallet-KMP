package com.easy.wallet.database

import app.cash.sqldelight.db.AfterVersion
import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.db.SqlSchema
import com.easy.wallet.database.adapters.DateTimeAdapter
import com.easy.wallet.database.adapters.DecimalsAdapter
import com.easy.wallet.database.platform.DatabaseDriverFactory
import com.easy.wallet.datastore.DatabaseKeyStorage

object Schema : SqlSchema<QueryResult.Value<Unit>> by WalletDatabase.Schema {
    override fun create(driver: SqlDriver): QueryResult.Value<Unit> {
        WalletDatabase.Schema.create(driver)
        WalletDatabase.Schema.migrate(
            driver,
            oldVersion = 0,
            newVersion = WalletDatabase.Schema.version,
            AfterVersion(2) { mDriver ->
                initDefaultData(mDriver)
            }
        )
        return QueryResult.Unit
    }
}

private fun initDefaultData(driver: SqlDriver) {
    driver.execute(
        null,
        """
            INSERT OR IGNORE INTO ChainEntity(id, name, website, explorer, rpc_url, chain_id)
            VALUES (-1, "Ethereum","https://ethereum.org/en/","https://etherscan.io","https://eth.llamarpc.com","0x1");
        """.trimIndent(),
        0
    )
    driver.execute(
        null,
        """
            INSERT OR IGNORE INTO ChainEntity(id, name, website, explorer, rpc_url, chain_id)
            VALUES (-2, "Ethereum(sepolia)","https://ethereum.org/en/","https://sepolia.etherscan.io/","https://sepolia.infura.io","0xaa36a7");
        """.trimIndent(),
        0
    )

    driver.execute(
        null,
        """
            INSERT OR ABORT INTO TokenEntity(id, chain_id, name, symbol, decimals, contract_address, icon_uri, is_active, tags)
            VALUES ("c60_t0xc00e94Cb662C3520282E6f5717214004A7f26888", -1, "Compound","COMP",18, "0xc00e94Cb662C3520282E6f5717214004A7f26888", "https://assets-cdn.trustwallet.com/blockchains/ethereum/assets/0xc00e94Cb662C3520282E6f5717214004A7f26888/logo.png",1, "ERC20");
        """.trimIndent(),
        0
    )
    driver.execute(
        null,
        """
            INSERT OR ABORT INTO TokenEntity(id, chain_id, name, symbol, decimals, contract_address, icon_uri, is_active, tags)
            VALUES ("c60_t0x1f9840a85d5aF5bf1D1762F925BDADdC4201F984", -1, "Uniswap","UNI",18, "0x1f9840a85d5aF5bf1D1762F925BDADdC4201F984", "https://assets-cdn.trustwallet.com/blockchains/ethereum/assets/0x1f9840a85d5aF5bf1D1762F925BDADdC4201F984/logo.png",1, "ERC20");
        """.trimIndent(),
        0
    )
    driver.execute(
        null,
        """
            INSERT OR ABORT INTO TokenEntity(id, chain_id, name, symbol, decimals, contract_address, icon_uri, is_active, tags)
            VALUES ("c60_t0xdAC17F958D2ee523a2206206994597C13D831ec7", -1, "Tether","USDT",18, "0xdAC17F958D2ee523a2206206994597C13D831ec7", "https://assets-cdn.trustwallet.com/blockchains/ethereum/assets/0xdAC17F958D2ee523a2206206994597C13D831ec7/logo.png",1, "ERC20");
        """.trimIndent(),
        0
    )
    driver.execute(
        null,
        """
            INSERT OR ABORT INTO TokenEntity(id, chain_id, name, symbol, decimals, contract_address, icon_uri, is_active, tags)
            VALUES ("c60_t0x6B175474E89094C44Da98b954EedeAC495271d0F", -1, "Dai","DAI",18, "0x6B175474E89094C44Da98b954EedeAC495271d0F", "https://assets-cdn.trustwallet.com/blockchains/ethereum/assets/0x6B175474E89094C44Da98b954EedeAC495271d0F/logo.png",1, "ERC20");
        """.trimIndent(),
        0
    )
    driver.execute(
        null,
        """
            INSERT OR ABORT INTO TokenEntity(id, chain_id, name, symbol, decimals, contract_address, icon_uri, is_active, tags)
            VALUES ("c60_t0x6B3595068778DD592e39A122f4f5a5cF09C90fE2", -1, "SushiSwap","SUSHI",18, "0x6B3595068778DD592e39A122f4f5a5cF09C90fE2", "https://assets-cdn.trustwallet.com/blockchains/ethereum/assets/0x6B3595068778DD592e39A122f4f5a5cF09C90fE2/logo.png",1, "ERC20");
        """.trimIndent(),
        0
    )
    driver.execute(
        null,
        """
            INSERT OR ABORT INTO TokenEntity(id, chain_id, name, symbol, decimals, contract_address, icon_uri, is_active, tags)
            VALUES ("ether_coin", -1, "Ethereum","ETH",18, null, "https://raw.githubusercontent.com/trustwallet/assets/master/blockchains/ethereum/info/logo.png",1, "");
        """.trimIndent(),
        0
    )

    /*driver.execute(
        null,
        """
            INSERT OR ABORT INTO TokenEntity(id, chain_id, name, symbol, decimals, contract_address, icon_uri, is_active, tags)
            VALUES ("ether_coin_sepolia", -2, "Ethereum","ETH",18, null, "https://raw.githubusercontent.com/trustwallet/assets/master/blockchains/ethereum/info/logo.png",1, "");
        """.trimIndent(),
        0
    )*/
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
            TokenEntityAdapter = TokenEntity.Adapter(
                decimalsAdapter = DecimalsAdapter()
            )
        )
    }
}
