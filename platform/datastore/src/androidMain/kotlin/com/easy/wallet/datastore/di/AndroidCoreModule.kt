package com.easy.wallet.datastore.di

import com.easy.wallet.datastore.platform.SharedUserDefaults
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual fun userDefaultModule() = module {
  singleOf(::SharedUserDefaults)
}
