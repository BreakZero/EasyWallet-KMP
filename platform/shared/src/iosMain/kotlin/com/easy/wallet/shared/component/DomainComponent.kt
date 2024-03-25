package com.easy.wallet.shared.component

import com.easy.wallet.shared.domain.CoinTrendUseCase
import com.easy.wallet.shared.domain.TokenAmountUseCase
import com.easy.wallet.shared.domain.TransactionPagerUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.experimental.ExperimentalObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName("DomainComponent")
class DomainComponent : KoinComponent {
    private val coinTrendUseCase: CoinTrendUseCase by inject()
    private val tokenAmountUseCase: TokenAmountUseCase by inject()
    private val transactionPagerUseCase: TransactionPagerUseCase by inject()
}
