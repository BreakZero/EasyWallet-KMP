package com.easy.wallet.onboard.di

import com.easy.wallet.domain.di.domainModule
import com.easy.wallet.onboard.create.CreateWalletViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val onboardModule = module {
    includes(domainModule)
    viewModelOf(::CreateWalletViewModel)
}