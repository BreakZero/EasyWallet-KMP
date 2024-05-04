package com.easy.wallet.model.asset

data class AssetBalance(
    override val id: String,
    override val name: String,
    override val symbol: String,
    override val decimalPlace: Int,
    override val logoURI: String,
    override val contract: String?,
    override val platform: AssetPlatform,
    val address: String,
    val balance: String,
) : CoinModel {
    companion object {
        val mockForPreview = AssetBalance(
            id = "_id",
            name = "_symbol",
            symbol = "_name",
            decimalPlace = 18,
            logoURI = "_logo",
            contract = null,
            platform = AssetPlatform("", "", null, null),
            address = "_address",
            balance = "0.0"
        )

        fun copyFromBasicCoin(coin: BasicCoin, address: String, balance: String): AssetBalance {
            return AssetBalance(
                id = coin.id,
                symbol = coin.symbol,
                decimalPlace = coin.decimalPlace,
                name = coin.name,
                logoURI = coin.logoURI,
                contract = coin.contract,
                platform = coin.platform,
                address = address,
                balance = balance
            )
        }

        fun copyFromAssetCoin(assetCoin: AssetCoin, balance: String): AssetBalance {
            return AssetBalance(
                id = assetCoin.id,
                symbol = assetCoin.symbol,
                decimalPlace = assetCoin.decimalPlace,
                name = assetCoin.name,
                logoURI = assetCoin.logoURI,
                contract = assetCoin.contract,
                platform = assetCoin.platform,
                address = assetCoin.address,
                balance = balance
            )
        }
    }
}
