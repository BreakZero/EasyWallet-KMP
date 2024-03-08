package com.easy.wallet.token_manager.di

import com.easy.wallet.token_manager.chain.ChainManagerViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

fun assetModule() = module {
    viewModelOf(::ChainManagerViewModel)
}
