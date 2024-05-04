package com.easy.wallet.shared.di

import com.easy.wallet.shared.data.multiwallet.MultiWalletRepository
import com.easy.wallet.shared.data.okx.OKXDataRepository
import com.easy.wallet.shared.data.repository.MarketsRepository
import com.easy.wallet.shared.data.repository.asset.ChainManageRepository
import com.easy.wallet.shared.data.repository.asset.CoinRepository
import com.easy.wallet.shared.data.repository.asset.LocalAssetRepository
import com.easy.wallet.shared.data.repository.asset.LocalChainManageRepository
import com.easy.wallet.shared.data.repository.asset.LocalTokenManageRepository
import com.easy.wallet.shared.data.repository.asset.PlatformRepository
import com.easy.wallet.shared.data.repository.asset.TokenManageRepository
import com.easy.wallet.shared.data.repository.chain.EvmChainRepository
import com.easy.wallet.shared.data.repository.chain.NoSupportedChainRepository
import com.easy.wallet.shared.data.repository.chain.OnChainRepository
import com.easy.wallet.shared.data.repository.news.NewsPager
import com.easy.wallet.shared.data.repository.news.NewsRepository
import com.easy.wallet.shared.domain.AllAssetDashboardUseCase
import com.easy.wallet.shared.domain.CoinBalanceUseCase
import com.easy.wallet.shared.domain.CoinTrendUseCase
import com.easy.wallet.shared.domain.CreateWalletUseCase
import com.easy.wallet.shared.domain.GetAssetCoinInfoUseCase
import com.easy.wallet.shared.domain.GetChainRepositoryUseCase
import com.easy.wallet.shared.domain.TransactionPagerUseCase
import com.easy.wallet.shared.domain.TransactionPlanUseCase
import com.easy.wallet.shared.domain.TransactionSigningUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

val sharedModule = module {
    single {
        MultiWalletRepository(get())
    }
    singleOf(::NewsRepository)

    single<OnChainRepository>(named("EvmChain")) { EvmChainRepository(get(), get()) }
    single<OnChainRepository>(named("NoSupportedChain")) { NoSupportedChainRepository() }

    single<CoinRepository>() { LocalAssetRepository(get(), get()) }
    single<PlatformRepository>() { LocalAssetRepository(get(), get()) }

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
        GetChainRepositoryUseCase(
            evmChainRepository = get(named("EvmChain")),
            noSupportedChainRepository = get(named("NoSupportedChain"))
        )
    }

    singleOf(::AllAssetDashboardUseCase)

    singleOf(::CoinTrendUseCase)

    singleOf(::CoinBalanceUseCase)

    singleOf(::GetAssetCoinInfoUseCase)

    singleOf(::TransactionPlanUseCase)

    singleOf(::TransactionSigningUseCase)

    single { NewsPager(get()) }

    singleOf(::TransactionPagerUseCase)

    singleOf(::CreateWalletUseCase)
}
