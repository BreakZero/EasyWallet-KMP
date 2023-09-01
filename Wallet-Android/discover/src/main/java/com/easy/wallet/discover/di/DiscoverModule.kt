package com.easy.wallet.discover.di

import com.easy.wallet.discover.DiscoverViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val discoverModule = module {
    viewModelOf(::DiscoverViewModel)
}