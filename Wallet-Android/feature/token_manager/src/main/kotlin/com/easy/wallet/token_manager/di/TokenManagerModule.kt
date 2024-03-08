package com.easy.wallet.token_manager.di

import com.easy.wallet.shared.data.repository.asset.LocalChainRepository
import com.easy.wallet.token_manager.chain.ChainManagerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

fun assetModule() = module {
    viewModel {
        ChainManagerViewModel(get(named<LocalChainRepository>()))
    }
}
