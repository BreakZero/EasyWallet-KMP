package com.easy.wallet.shared.di

import com.easy.wallet.core.di.dispatcherModule
import com.easy.wallet.database.di.databaseModule
import com.easy.wallet.datastore.DatabaseKeyStorage
import com.easy.wallet.datastore.UserPasswordStorage
import com.easy.wallet.datastore.di.userDefaultModule
import com.easy.wallet.network.di.networkModule
import com.easy.wallet.shared.data.global.HDWalletInstant
import com.easy.wallet.shared.data.multiwallet.MultiWalletRepository
import com.easy.wallet.shared.data.okx.OKXDataRepository
import com.easy.wallet.shared.data.repository.BitcoinRepository
import com.easy.wallet.shared.data.repository.EthereumRepository
import com.easy.wallet.shared.data.repository.MarketsRepository
import com.easy.wallet.shared.data.repository.SupportedTokenRepository
import com.easy.wallet.shared.data.repository.TokenRepository
import com.easy.wallet.shared.data.repository.asset.ChainManageRepository
import com.easy.wallet.shared.data.repository.asset.TokenManageRepository
import com.easy.wallet.shared.data.repository.asset.LocalChainManageRepository
import com.easy.wallet.shared.data.repository.asset.LocalTokenManageRepository
import com.easy.wallet.shared.data.repository.news.NewsRepository
import com.easy.wallet.shared.data.repository.transactions.EthereumTransactionRepository
import com.easy.wallet.shared.data.repository.transactions.TransactionRepository
import com.easy.wallet.shared.domain.CoinTrendUseCase
import com.easy.wallet.shared.domain.CreateWalletUseCase
import com.easy.wallet.shared.domain.DashboardUseCase
import com.easy.wallet.shared.domain.TokenAmountUseCase
import com.easy.wallet.shared.domain.TransactionsUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val sharedModule = module {
    includes(dispatcherModule)
    includes(userDefaultModule())
    includes(networkModule)
    includes(databaseModule)
    singleOf(::UserPasswordStorage)
    singleOf(::DatabaseKeyStorage)
    single {
        MultiWalletRepository(get())
    }
    singleOf(::NewsRepository)
    singleOf(::SupportedTokenRepository)
    singleOf(::HDWalletInstant)

    single<TokenRepository>(named("Bitcoin")) { BitcoinRepository() }
    single<TokenRepository>(named("Ethereum")) { EthereumRepository(get()) }

    single<ChainManageRepository>(named<LocalChainManageRepository>()) { LocalChainManageRepository(get()) }
    single<TokenManageRepository>(named<LocalTokenManageRepository>()) { LocalTokenManageRepository(get()) }

    single<TransactionRepository>(named("Ethereum")) { EthereumTransactionRepository(get()) }

    singleOf(::MarketsRepository)

    single { OKXDataRepository(get()) }

    single {
        TransactionsUseCase(
            walletRepository = get(),
            ethereumTransactionRepository = get(named("Ethereum")),
        )
    }

    single {
        DashboardUseCase(
            supportedTokenRepository = get(),
            ethereumRepository = get(named("Ethereum")),
            bitcoinRepository = get(named("Bitcoin")),
        )
    }

    single {
        CoinTrendUseCase(
            walletRepository = get(),
            ethereumRepository = get(named("Ethereum")),
            bitcoinRepository = get(named("Bitcoin")),
        )
    }

    single {
        TokenAmountUseCase(
            walletRepository = get(),
            ethereumRepository = get(named("Ethereum")),
            bitcoinRepository = get(named("Bitcoin")),
        )
    }

    singleOf(::CreateWalletUseCase)
}
