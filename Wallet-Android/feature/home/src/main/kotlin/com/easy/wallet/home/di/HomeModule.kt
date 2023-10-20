package com.easy.wallet.home.di

import com.easy.wallet.home.HomeViewModel
import com.easy.wallet.home.transactions.TransactionsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val homeModule = module {
    viewModelOf(::HomeViewModel)
    viewModelOf(::TransactionsViewModel)
}
