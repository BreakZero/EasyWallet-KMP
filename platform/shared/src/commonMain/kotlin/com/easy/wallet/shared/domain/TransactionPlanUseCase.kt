package com.easy.wallet.shared.domain

import com.easy.wallet.shared.model.fees.FeeModel
import com.ionspin.kotlin.bignum.decimal.toBigDecimal
import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TransactionPlanUseCase internal constructor(
    private val basicInfoUseCase: GetToKenBasicInfoUseCase,
    private val exactTokenRepositoryUseCase: GetExactTokenRepositoryUseCase
) {
    @NativeCoroutines
    operator fun invoke(
        tokenId: String,
        toAddress: String,
        amount: String,
    ): Flow<List<FeeModel>> {
        return basicInfoUseCase(tokenId).map { basicInfo ->
            exactTokenRepositoryUseCase(basicInfo).prepFees(
                account = basicInfo.address,
                contractAddress = basicInfo.contract,
                toAddress = toAddress,
                amount = amount.toBigDecimal().moveDecimalPoint(basicInfo.decimals).toBigInteger()
                    .toString(16)
            )
        }
    }
}
