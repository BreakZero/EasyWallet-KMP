package com.easy.wallet.database

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