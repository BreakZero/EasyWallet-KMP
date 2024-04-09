package com.easy.wallet.shared.domain

import com.ionspin.kotlin.bignum.decimal.RoundingMode
import com.ionspin.kotlin.bignum.decimal.toBigDecimal
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TokenBalanceUseCase internal constructor(
    private val getToKenBasicInfoUseCase: GetToKenBasicInfoUseCase,
    private val getExactTokenRepositoryUseCase: GetExactTokenRepositoryUseCase
) {
    @NativeCoroutines
    operator fun invoke(tokenId: String): Flow<String> {
        return getToKenBasicInfoUseCase(tokenId).map { basicInfo ->
            val exactRepository = getExactTokenRepositoryUseCase(basicInfo)
            val balance = exactRepository.loadBalance(basicInfo.address, basicInfo.contract)
            // format to display Model
            balance.toBigDecimal(exponentModifier = -basicInfo.decimals.toLong())
                .roundToDigitPositionAfterDecimalPoint(8, RoundingMode.ROUND_HALF_CEILING)
                .toPlainString()
        }
    }
}


