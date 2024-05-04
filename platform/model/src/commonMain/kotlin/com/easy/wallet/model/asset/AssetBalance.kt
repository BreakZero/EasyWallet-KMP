package com.easy.wallet.model.asset

data class AssetBalance(
    override val id: String,
    override val symbol: String,
    override val name: String,
    override val logoURI: String,
    override val contract: String?,
    override val platform: AssetPlatform,
    val address: String,
    val balance: String
) : CoinModel {
    companion object {
        val mockForPreview = AssetBalance(
            "_id", "_symbol", "_name",
            "_logo", null, AssetPlatform("", "", null, null),
            "_address", "0.0"
        )
    }
}
