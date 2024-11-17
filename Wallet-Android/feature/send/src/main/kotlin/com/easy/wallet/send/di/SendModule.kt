package com.easy.wallet.send.di

import com.easy.wallet.send.SendSharedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val sendModule = module {
  viewModel { parameter ->
    SendSharedViewModel(
      coinId = parameter.get(),
      get(),
      get(),
      get(),
      get()
    )
  }
}
