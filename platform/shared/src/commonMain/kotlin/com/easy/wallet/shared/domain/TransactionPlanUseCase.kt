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
        toAddress: String,
        amount: String,
    ): Flow<List<FeeModel>> {
        return getAssetCoinInfoUseCase(coinId).map { assetCoin ->
            getChainRepositoryUseCase(assetCoin.platform).prepFees(
                coin = assetCoin,
                toAddress = toAddress,
                amount = amount.toBigDecimal().moveDecimalPoint(assetCoin.decimalPlace)
                    .toBigInteger()
                    .toString(16)
            )
        }
    }
}
