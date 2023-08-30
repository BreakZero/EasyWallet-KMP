package com.easy.wallet.domain.di

import com.easy.wallet.domain.hdwallet.CreateWalletUseCase
import org.koin.dsl.module

val domainModule = module {
    single { CreateWalletUseCase(get()) }
}