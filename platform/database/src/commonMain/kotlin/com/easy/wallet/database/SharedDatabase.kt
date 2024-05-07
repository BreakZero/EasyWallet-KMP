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
            AfterVersion(3) {
                initSupportedPlatform(it)
                initDefaultCoins(it)
            }
        )
        return QueryResult.Unit
    }
}

private fun initSupportedPlatform(driver: SqlDriver) {
    driver.execute(
        identifier = null,
        """
            INSERT OR IGNORE INTO AssetPlatform(id, chain_identifier, short_name, is_testnet, evm_network_info)
            VALUES('ethereum', '1', 'Ethereum', 0, '{"networkName":"ethereum","rpcUrl":"https://rpc.flashbots.net/","explorerUrl":null}');
        """.trimIndent(),
        parameters = 0
    )
    driver.execute(
        identifier = null,
        """
            INSERT OR IGNORE INTO AssetPlatform(id, chain_identifier, short_name, is_testnet, evm_network_info)
            VALUES('ethereum_sepolia', '11155111', 'Ethereum(Sepolia)', 1, '{"networkName":"ethereum(sepolia)","rpcUrl":"https://1rpc.io/sepolia/","explorerUrl":null}');
        """.trimIndent(),
        parameters = 0
    )
}

private fun initDefaultCoins(driver: SqlDriver) {
    driver.execute(
        identifier = null,
        """
            INSERT OR IGNORE INTO CoinEntity(id, platform_id, symbol, name, logo_uri, contract, decimal_place, is_active)
            VALUES('ethereum','ethereum','ETH','Ethereum','https://assets.coingecko.com/coins/images/279/small/ethereum.png',NULL,18 ,1);
        """.trimIndent(),
        parameters = 0
    )
    driver.execute(
        identifier = null,
        """
            INSERT OR IGNORE INTO CoinEntity(id, platform_id, symbol, name, logo_uri, contract, decimal_place, is_active)
            VALUES('dai','ethereum','DAI','Dai','https://assets.coingecko.com/coins/images/9956/small/Badge_Dai.png', '0x6b175474e89094c44da98b954eedeac495271d0f',18 ,1);
        """.trimIndent(),
        parameters = 0
    )
    driver.execute(
        identifier = null,
        sql = """
            INSERT OR IGNORE INTO CoinEntity(id, platform_id, symbol, name, logo_uri, contract, decimal_place, is_active)
            VALUES('uniswap','ethereum','UNI','Uniswap','https://assets.coingecko.com/coins/images/12504/small/uni.jpg', '0x1f9840a85d5af5bf1d1762f925bdaddc4201f984',18 ,1);
        """.trimIndent(),
        parameters = 0
    )
    driver.execute(
        identifier = null,
        """
            INSERT OR IGNORE INTO CoinEntity(id, platform_id, symbol, name, logo_uri, contract, decimal_place, is_active)
            VALUES('compound-governance-token','ethereum','COMP','Compound','https://assets.coingecko.com/coins/images/10775/small/COMP.png', '0xc00e94cb662c3520282e6f5717214004a7f26888',18 ,1);
        """.trimIndent(),
        parameters = 0
    )
    driver.execute(
        identifier = null,
        """
            INSERT OR IGNORE INTO CoinEntity(id, platform_id, symbol, name, logo_uri, contract, decimal_place, is_active)
            VALUES('crypto-com-chain','ethereum','CRO','Cronos','https://assets.coingecko.com/coins/images/7310/small/cro_token_logo.png', '0xa0b73e1ff0b80914ab6fe0444e65848c4c34450b',8 ,1);
        """.trimIndent(),
        parameters = 0
    )
    driver.execute(
        identifier = null,
        """
            INSERT OR IGNORE INTO CoinEntity(id, platform_id, symbol, name, logo_uri, contract, decimal_place, is_active)
            VALUES('binancecoin','ethereum','BNB','Binance Coin','https://assets.coingecko.com/coins/images/825/small/bnb-icon2_2x.png', '0xb8c77482e45f1f44de1745f52c74426c631bdd52',18 ,1);
        """.trimIndent(),
        parameters = 0
    )
    /** --------------------------- ------------------------------- **/
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
            CoinEntityAdapter = CoinEntity.Adapter(
                decimal_placeAdapter = DecimalsAdapter()
            ),
            TokenEntityAdapter = TokenEntity.Adapter(
                decimalsAdapter = DecimalsAdapter()
            )
        )
    }
}
