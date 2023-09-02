package com.easy.wallet.database

import app.cash.sqldelight.db.SqlDriver
import com.easy.wallet.database.adapters.DateTimeAdapter
import com.easy.wallet.database.adapters.DecimalsAdapter
import com.easy.wallet.database.adapters.NetworkAdapter
import com.easy.wallet.database.adapters.TokenTypeAdapter
import comeasywalletdatabase.HdWalletEntity
import migrations.Contract
import migrations.Token

fun createQueryWrapper(driver: SqlDriver): WalletDatabase {
    return WalletDatabase(
        driver = driver,
        hdWalletEntityAdapter = HdWalletEntity.Adapter(createAtAdapter = DateTimeAdapter()),
        tokenAdapter = Token.Adapter(
            decimalsAdapter = DecimalsAdapter(),
            typeAdapter = TokenTypeAdapter()
        ),
        contractAdapter = Contract.Adapter(networkAdapter = NetworkAdapter())
    )
}

interface TokenVals {
    enum class TokenType {
        TOKEN,
        ERC20
    }

    enum class Network(
        val label: String
    ) {
        ETH_MAIN("Ethereum"),
        POLYGON_MAIN("polygon");

        companion object {
            fun from(label: String): Network {
                return when (label) {
                    "Ethereum" -> ETH_MAIN
                    "polygon" -> POLYGON_MAIN
                    else -> ETH_MAIN
                }
            }
        }
    }
}