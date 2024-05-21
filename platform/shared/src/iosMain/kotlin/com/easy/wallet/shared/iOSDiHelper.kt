package com.easy.wallet.shared

import com.easy.wallet.shared.data.multiwallet.MultiWalletRepository
import com.easy.wallet.shared.data.repository.MarketsRepository
import com.easy.wallet.shared.data.repository.news.NewsPager
import com.easy.wallet.shared.di.initKoin
import com.easy.wallet.shared.domain.AllAssetDashboardUseCase
import com.easy.wallet.shared.domain.CoinBalanceUseCase
import com.easy.wallet.shared.domain.CoinTrendUseCase
import com.easy.wallet.shared.domain.TransactionPagerUseCase
import org.koin.core.Koin
import org.koin.core.KoinApplication

fun KoinApplication.Companion.start(): KoinApplication = initKoin {}

val Koin.allAssetDashboard: AllAssetDashboardUseCase
    get() = get()

val Koin.coinTrendUseCase: CoinTrendUseCase
    get() = get()

val Koin.coinBalanceUseCase: CoinBalanceUseCase
    get() = get()

val Koin.transactionPagerUseCase: TransactionPagerUseCase
    get() = get()

val Koin.multiWalletRepository: MultiWalletRepository
    get() = get()

val Koin.newsPager: NewsPager
    get() = get()

val Koin.marketRepository: MarketsRepository
    get() = get()
