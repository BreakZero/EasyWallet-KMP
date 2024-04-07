package com.easy.wallet.shared.di

import com.easy.wallet.datastore.DatabaseKeyStorage
import com.easy.wallet.datastore.UserPasswordStorage
import com.easy.wallet.shared.data.multiwallet.MultiWalletRepository
import com.easy.wallet.shared.data.okx.OKXDataRepository
import com.easy.wallet.shared.data.repository.BitcoinRepository
import com.easy.wallet.shared.data.repository.EthereumRepository
import com.easy.wallet.shared.data.repository.MarketsRepository
import com.easy.wallet.shared.data.repository.NoSupportedTokenRepository
import com.easy.wallet.shared.data.repository.SupportedTokenRepository
import com.easy.wallet.shared.data.repository.TokenRepository
import com.easy.wallet.shared.data.repository.asset.ChainManageRepository
import com.easy.wallet.shared.data.repository.asset.LocalChainManageRepository
import com.easy.wallet.shared.data.repository.asset.LocalTokenManageRepository
import com.easy.wallet.shared.data.repository.asset.TokenManageRepository
import com.easy.wallet.shared.data.repository.news.NewsPager
import com.easy.wallet.shared.data.repository.news.NewsRepository
import com.easy.wallet.shared.domain.CoinTrendUseCase
import com.easy.wallet.shared.domain.CreateWalletUseCase
import com.easy.wallet.shared.domain.DashboardUseCase
import com.easy.wallet.shared.domain.GetExactTokenRepositoryUseCase
import com.easy.wallet.shared.domain.GetToKenBasicInfoUseCase
import com.easy.wallet.shared.domain.TokenBalanceUseCase
import com.easy.wallet.shared.domain.TransactionPagerUseCase
import com.easy.wallet.shared.domain.TransactionSigningUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val sharedModule = module {
    singleOf(::UserPasswordStorage)
    singleOf(::DatabaseKeyStorage)
    single {
        MultiWalletRepository(get())
    }
    singleOf(::NewsRepository)
    singleOf(::SupportedTokenRepository)

    single<TokenRepository>(named("Bitcoin")) { BitcoinRepository() }
    single<TokenRepository>(named("Ethereum")) { EthereumRepository(get(), get(), get()) }
    single<TokenRepository>(named("NoSupportedChain")) { NoSupportedTokenRepository() }

    single<ChainManageRepository>(named<LocalChainManageRepository>()) {
        LocalChainManageRepository(
            get()
        )
    }
    single<TokenManageRepository>(named<LocalTokenManageRepository>()) {
        LocalTokenManageRepository(
            get()
        )
    }

    singleOf(::MarketsRepository)

    single { OKXDataRepository(get()) }

    single {
        GetExactTokenRepositoryUseCase(
            ethereumRepository = get(named("Ethereum")),
            bitcoinRepository = get(named("Bitcoin"))
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
            getToKenBasicInfoUseCase = get(),
            getExactTokenRepositoryUseCase = get()
        )
    }

    single {
        TokenBalanceUseCase(
            getToKenBasicInfoUseCase = get(),
            getExactTokenRepositoryUseCase = get()
        )
    }

    single {
        GetToKenBasicInfoUseCase(
            walletRepository = get(),
            supportedTokenRepository = get()
        )
    }

    single {
        TransactionSigningUseCase(
            walletRepository = get(),
            basicInfoUseCase = get(),
            exactTokenRepositoryUseCase = get()
        )
    }

    single { NewsPager(get()) }

    single {
        TransactionPagerUseCase(
            getToKenBasicInfoUseCase = get(),
            getExactTokenRepositoryUseCase = get()
        )
    }

    singleOf(::CreateWalletUseCase)
}
