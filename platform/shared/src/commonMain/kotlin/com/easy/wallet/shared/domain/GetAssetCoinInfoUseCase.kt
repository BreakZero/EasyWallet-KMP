package com.easy.wallet.shared.domain

import com.easy.wallet.core.commom.AssetPlatformIdConstant
import com.easy.wallet.model.asset.AssetCoin
import com.easy.wallet.model.asset.AssetPlatform
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
  operator fun invoke(coinId: String): Flow<AssetCoin> = flow {
    val wallet = walletRepository.findWallet()
      ?: throw NoSuchElementException("No wallet had been set yet.")
    val coin = coinRepository.findCoinById(coinId)
      ?: throw NoSuchElementException("No coin be found, id is: $coinId")

    val hdWallet = HDWallet(wallet.mnemonic, wallet.passphrase)
    val (address, keyData) = getAddressAndKey(hdWallet, coin.platform)
    emit(
      AssetCoin.copyFromBasicCoin(coin, address, keyData)
    )
  }
}

private fun getAddressAndKey(hdWallet: HDWallet, platform: AssetPlatform): Pair<String, ByteArray> {
  val coinType = when (platform.id) {
    AssetPlatformIdConstant.PLATFORM_ETHEREUM, AssetPlatformIdConstant.PLATFORM_ETHEREUM_SEPOLIA -> CoinType.Ethereum
    else -> throw NotImplementedError("The chain(${platform.shortName}) not supported yet.")
  }
  val address = hdWallet.getAddressForCoin(coinType)
  val keyData = hdWallet.getKeyForCoin(coinType).data
  return Pair(address, keyData)
}
