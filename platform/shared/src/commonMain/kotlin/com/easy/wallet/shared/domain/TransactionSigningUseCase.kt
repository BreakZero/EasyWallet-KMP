package com.easy.wallet.shared.domain

import com.easy.wallet.core.commom.AssetPlatformIdConstant
import com.easy.wallet.model.asset.AssetPlatform
import com.easy.wallet.shared.data.multiwallet.MultiWalletRepository
import com.easy.wallet.shared.model.fees.FeeModel
import com.ionspin.kotlin.bignum.decimal.toBigDecimal
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import com.trustwallet.core.CoinType
import com.trustwallet.core.HDWallet
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionSigningUseCase internal constructor(
    private val walletRepository: MultiWalletRepository,
    private val getAssetCoinInfoUseCase: GetAssetCoinInfoUseCase,
    private val getChainRepositoryUseCase: GetChainRepositoryUseCase
) {

    @NativeCoroutines
    operator fun invoke(
        coinId: String,
        toAddress: String,
        amount: String,
        fee: FeeModel
    ): Flow<String> {
        return getAssetCoinInfoUseCase(coinId).map { assetCoin ->
            // active wallet flow never stop, we need the first one when signing is OK
            val wallet = walletRepository.forActiveOne()
                ?: throw NoSuchElementException("No wallet had been set yet.")

            val hdWallet = HDWallet(wallet.mnemonic, wallet.passphrase)
            val privateKey = getPrivateKeyFromChain(hdWallet, assetCoin.platform)
            getChainRepositoryUseCase(assetCoin.platform).signAndBroadcast(
                account = assetCoin.address,
                // update chain id to hex string
                chainId = assetCoin.platform.chainIdentifier ?: "0x01",
                privateKey = privateKey,
                contractAddress = assetCoin.contract,
                toAddress = toAddress,
                amount = amount.toBigDecimal().moveDecimalPoint(assetCoin.decimalPlace)
                    .toBigInteger()
                    .toString(16),
                fee = fee
            )
        }
    }

    private fun getPrivateKeyFromChain(
        hdWallet: HDWallet,
        platform: AssetPlatform
    ): ByteArray {
        val coinType = when (platform.id) {
            AssetPlatformIdConstant.PLATFORM_ETHEREUM, AssetPlatformIdConstant.PLATFORM_ETHEREUM_SEPOLIA -> CoinType.Ethereum
            else -> throw NotImplementedError("The chain(${platform.shortName}) not supported yet.")
        }
        return hdWallet.getKeyForCoin(coinType).data
    }
}
