package com.easy.wallet.model.asset

data class AssetCoin(
    override val id: String,
    override val name: String,
    override val symbol: String,
    override val decimalPlace: Int,
    override val logoURI: String,
    override val contract: String?,
    override val platform: AssetPlatform,
    val address: String
) : CoinModel {
    companion object {
        fun copyFromBasicCoin(coin: BasicCoin, address: String): AssetCoin {
            return AssetCoin(
                id = coin.id,
                symbol = coin.symbol,
                decimalPlace = coin.decimalPlace,
                name = coin.name,
                logoURI = coin.logoURI,
                contract = coin.contract,
                platform = coin.platform,
                address = address
            )
        }
    }
}
