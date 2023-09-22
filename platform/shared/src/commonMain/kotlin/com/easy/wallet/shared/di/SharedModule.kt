package com.easy.wallet.shared.di

import com.easy.wallet.database.di.databaseModule
import com.easy.wallet.datastore.DatabaseKeyStorage
import com.easy.wallet.datastore.UserPasswordStorage
import com.easy.wallet.datastore.di.userDefaultModule
import com.easy.wallet.network.di.networkModule
import com.easy.wallet.shared.data.global.HDWalletInstant
import com.easy.wallet.shared.data.multiwallet.MultiWalletRepository
import com.easy.wallet.shared.data.repository.BitcoinRepository
import com.easy.wallet.shared.data.repository.EthereumRepository
import com.easy.wallet.shared.data.repository.SupportedTokenRepository
import com.easy.wallet.shared.data.repository.TokenRepository
import com.easy.wallet.shared.data.repository.news.NewsRepository
import com.easy.wallet.shared.domain.CreateWalletUseCase
import com.easy.wallet.shared.domain.DashboardUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val sharedModule = module {
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

    single {
        DashboardUseCase(
            hdWalletInstant = get(),
            supportedTokenRepository = get(),
            ethereumRepository = get(named("Ethereum")),
            bitcoinRepository = get(named("Bitcoin")),
        )
    }
    singleOf(::CreateWalletUseCase)
}
