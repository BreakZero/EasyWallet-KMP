package com.easy.wallet.shared.domain

import com.easy.wallet.shared.model.fees.FeeModel
import com.ionspin.kotlin.bignum.decimal.toBigDecimal
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionPlanUseCase internal constructor(
    private val getAssetCoinInfoUseCase: GetAssetCoinInfoUseCase,
    private val getChainRepositoryUseCase: GetChainRepositoryUseCase
) {
    @NativeCoroutines
    operator fun invoke(
        coinId: String,
        platformId: String,
        toAddress: String,
        amount: String,
    ): Flow<List<FeeModel>> {
        return getAssetCoinInfoUseCase(coinId, platformId).map { assetCoin ->
            getChainRepositoryUseCase(assetCoin.platform).prepFees(
                account = assetCoin.address,
                contractAddress = assetCoin.contract,
                toAddress = toAddress,
                amount = amount.toBigDecimal().moveDecimalPoint(assetCoin.decimalPlace)
                    .toBigInteger()
                    .toString(16)
            )
        }
    }
}
