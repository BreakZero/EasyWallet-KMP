package com.easy.wallet.assetmanager.di

import com.easy.wallet.assetmanager.coin.editor.TokenEditorViewModel
import com.easy.wallet.assetmanager.coin.manager.TokenManagerViewModel
import com.easy.wallet.assetmanager.platform.detail.ChainDetailViewModel
import com.easy.wallet.assetmanager.platform.editor.ChainEditorViewModel
import com.easy.wallet.assetmanager.platform.manager.ChainManagerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

fun assetModule() = module {
  viewModel {
    ChainManagerViewModel(get())
  }
  viewModel {
    ChainEditorViewModel(get(), get())
  }

  viewModel { ChainDetailViewModel(get(), get()) }

  viewModelOf(::TokenManagerViewModel)

  viewModelOf(::TokenEditorViewModel)
}
