package com.easy.wallet.shared.domain

import com.ionspin.kotlin.bignum.decimal.RoundingMode
import com.ionspin.kotlin.bignum.decimal.toBigDecimal
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class CoinBalanceUseCase internal constructor(
    private val assetCoinInfoUseCase: GetAssetCoinInfoUseCase,
    private val getChainRepositoryUseCase: GetChainRepositoryUseCase
) {
    @NativeCoroutines
    operator fun invoke(coinId: String): Flow<String> {
        return assetCoinInfoUseCase(coinId).map { assetCoin ->
            val exactChainRepository = getChainRepositoryUseCase(assetCoin.platform)
            val balance = exactChainRepository.loadBalance(assetCoin.address, assetCoin.contract)
            // format to display Model
            balance.toBigDecimal(
                exponentModifier = -(assetCoin.platform.network?.decimalPlace ?: 18).toLong()
            ).roundToDigitPositionAfterDecimalPoint(8, RoundingMode.ROUND_HALF_CEILING)
                .toPlainString()
        }
    }
}


