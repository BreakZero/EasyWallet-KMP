package com.easy.wallet.marketplace.di

import com.easy.wallet.marketplace.MarketplaceViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val marketModule = module {
    viewModelOf(::MarketplaceViewModel)
}