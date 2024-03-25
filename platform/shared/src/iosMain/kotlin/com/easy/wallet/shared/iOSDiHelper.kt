package com.easy.wallet.shared

import com.easy.wallet.shared.data.multiwallet.MultiWalletRepository
import com.easy.wallet.shared.data.repository.news.NewsPager
import com.easy.wallet.shared.di.initKoin
import com.easy.wallet.shared.domain.CoinTrendUseCase
import com.easy.wallet.shared.domain.DashboardUseCase
import com.easy.wallet.shared.domain.TokenAmountUseCase
import com.easy.wallet.shared.domain.TransactionPagerUseCase
import org.koin.core.Koin
import org.koin.core.KoinApplication

fun KoinApplication.Companion.start(): KoinApplication = initKoin {}

val Koin.dashboardUseCase: DashboardUseCase
    get() = get()

val Koin.coinTrendUseCase: CoinTrendUseCase
    get() = get()

val Koin.tokenAmountUseCase: TokenAmountUseCase
    get() = get()

val Koin.transactionPagerUseCase: TransactionPagerUseCase
    get() = get()

val Koin.multiWalletRepository: MultiWalletRepository
    get() = get()

val Koin.newsPager: NewsPager
    get() = get()
