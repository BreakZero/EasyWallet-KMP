package com.easy.wallet.home.di

import com.easy.wallet.home.WalletViewModel
import com.easy.wallet.home.transactions.TransactionsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val homeModule = module {
  viewModelOf(::WalletViewModel)
  viewModel { parameter ->
    TransactionsViewModel(coinId = parameter.get(), get(), get(), get())
  }
}
