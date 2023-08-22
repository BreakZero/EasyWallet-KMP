package com.easy.wallet.onboard.di

import com.easy.wallet.onboard.create.password.CreatePasswordViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val onboardModule = module {
    viewModelOf(::CreatePasswordViewModel)
}