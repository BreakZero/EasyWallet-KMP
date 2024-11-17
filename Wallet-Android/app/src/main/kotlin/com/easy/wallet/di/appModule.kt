package com.easy.wallet.di

import com.easy.wallet.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
  viewModelOf(::MainViewModel)
}
