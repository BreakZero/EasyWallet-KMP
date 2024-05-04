package com.easy.wallet.shared.domain

import com.easy.wallet.core.commom.AssetPlatformIdConstant
import com.easy.wallet.model.asset.AssetCoin
import com.easy.wallet.shared.data.multiwallet.MultiWalletRepository
import com.easy.wallet.shared.data.repository.asset.CoinRepository
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.trustwallet.core.CoinType
import com.trustwallet.core.HDWallet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetAssetCoinInfoUseCase internal constructor(
    private val walletRepository: MultiWalletRepository,
    private val coinRepository: CoinRepository
) {
    @NativeCoroutines
    operator fun invoke(coinId: String): Flow<AssetCoin> {
        return flow {
            val wallet = walletRepository.findWallet()
                ?: throw NoSuchElementException("No wallet had been set yet.")
            val coin = coinRepository.findCoinById(coinId)
                ?: throw NoSuchElementException("No coin be found, id is: $coinId")

            val hdWallet = HDWallet(wallet.mnemonic, wallet.passphrase)
            val address = getAddress(hdWallet, coin.platform.id)
            emit(
                AssetCoin.copyFromBasicCoin(coin, address)
            )
        }
    }
}

private fun getAddress(hdWallet: HDWallet, platform: String): String {
    val coinType = when (platform) {
        AssetPlatformIdConstant.PLATFORM_ETHEREUM, AssetPlatformIdConstant.PLATFORM_ETHEREUM_SEPOLIA -> CoinType.Ethereum
        else -> null
    }
    return coinType?.let { hdWallet.getAddressForCoin(it) } ?: ""
}
