package com.easy.wallet.send.di

import com.easy.wallet.send.SendSharedViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val sendModule = module {
    viewModelOf(::SendSharedViewModel)
}
