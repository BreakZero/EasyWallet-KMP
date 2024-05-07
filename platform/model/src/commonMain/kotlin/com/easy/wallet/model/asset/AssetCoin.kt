package com.easy.wallet.model.asset

data class AssetCoin(
    override val id: String,
    override val name: String,
    override val symbol: String,
    override val decimalPlace: Int,
    override val logoURI: String,
    override val contract: String?,
    override val platform: AssetPlatform,
    val address: String,
    val privateKey: ByteArray
) : CoinModel {
    companion object {
        fun copyFromBasicCoin(coin: BasicCoin, address: String, keyData: ByteArray): AssetCoin {
            return AssetCoin(
                id = coin.id,
                symbol = coin.symbol,
                decimalPlace = coin.decimalPlace,
                name = coin.name,
                logoURI = coin.logoURI,
                contract = coin.contract,
                platform = coin.platform,
                address = address,
                privateKey = keyData
            )
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other == null || this::class != other::class) return false

        other as AssetCoin

        if (id != other.id) return false
        if (name != other.name) return false
        if (symbol != other.symbol) return false
        if (decimalPlace != other.decimalPlace) return false
        if (logoURI != other.logoURI) return false
        if (contract != other.contract) return false
        if (platform != other.platform) return false
        if (address != other.address) return false
        if (!privateKey.contentEquals(other.privateKey)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + name.hashCode()
        result = 31 * result + symbol.hashCode()
        result = 31 * result + decimalPlace
        result = 31 * result + logoURI.hashCode()
        result = 31 * result + (contract?.hashCode() ?: 0)
        result = 31 * result + platform.hashCode()
        result = 31 * result + address.hashCode()
        result = 31 * result + privateKey.contentHashCode()
        return result
    }
}
