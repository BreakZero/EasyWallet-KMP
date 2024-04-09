package com.easy.wallet.shared.domain

import com.rickclephas.kmp.nativecoroutines.NativeCoroutines
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlin.random.Random

class CoinTrendUseCase internal constructor(
    private val getToKenBasicInfoUseCase: GetToKenBasicInfoUseCase,
    private val getExactTokenRepositoryUseCase: GetExactTokenRepositoryUseCase
) {
    @NativeCoroutines
    operator fun invoke(tokenId: String): Flow<List<String>> {
        // check which repository should be used

        // mock result for right now
        return getToKenBasicInfoUseCase(tokenId).map { _ ->
            List(10) { Random.nextInt(12).toString() }
        }
    }
}
