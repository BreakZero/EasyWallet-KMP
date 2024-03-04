package com.easy.wallet.database

interface CoinVals {
    enum class CoinType {
        COIN,
        ERC20
    }

    enum class ChainLayer2Type {
        ERC20
    }

    enum class ChainNetwork(
        val label: String
    ) {
        ETH_MAIN("Ethereum"),
        POLYGON_MAIN("polygon");

        companion object {
            fun from(label: String): ChainNetwork {
                return when (label) {
                    "Ethereum" -> ETH_MAIN
                    "polygon" -> POLYGON_MAIN
                    else -> ETH_MAIN
                }
            }
        }
    }
}
