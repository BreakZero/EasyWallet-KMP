package com.easy.wallet.settings.di

import com.easy.wallet.settings.SettingsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val settingsModule = module {
  viewModel {
    SettingsViewModel()
  }
}
