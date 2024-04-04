package com.easy.wallet.shared.domain

import com.ionspin.kotlin.bignum.decimal.RoundingMode
import com.ionspin.kotlin.bignum.decimal.toBigDecimal
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.map

class TokenAmountUseCase internal constructor(
    private val getToKenBasicInfoUseCase: GetToKenBasicInfoUseCase,
    private val getExactTokenRepositoryUseCase: GetExactTokenRepositoryUseCase
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    @Throws(NoSuchElementException::class)
    operator fun invoke(tokenId: String): Flow<String> {
        return getToKenBasicInfoUseCase(tokenId).flatMapConcat { basicInfo ->
            val exactRepository = getExactTokenRepositoryUseCase(basicInfo)
            exactRepository.loadBalance(basicInfo.address).map {
                val balance = it.toBigDecimal().moveDecimalPoint(-basicInfo.decimals)
                    .roundToDigitPositionAfterDecimalPoint(8, RoundingMode.ROUND_HALF_CEILING)
                    .toPlainString()
                "$balance ${basicInfo.symbol}"
            }
        }
    }
}


