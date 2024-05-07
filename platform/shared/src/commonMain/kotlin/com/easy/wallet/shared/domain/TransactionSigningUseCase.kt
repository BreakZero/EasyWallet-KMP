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
            getChainRepositoryUseCase(assetCoin.platform).signAndBroadcast(
                coin = assetCoin,
                toAddress = toAddress,
                amount = amount.toBigDecimal().moveDecimalPoint(assetCoin.decimalPlace)
                    .toBigInteger()
                    .toString(16),
                fee = fee
            )
        }
    }
}
