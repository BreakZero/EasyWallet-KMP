package com.easy.wallet.core.di

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.qualifier.named
import org.koin.dsl.module

enum class EasyDispatchers {
  Default,
  IO
}

val dispatcherModule = module {
  single(named(EasyDispatchers.IO.name)) { Dispatchers.IO }
  single(named(EasyDispatchers.Default.name)) { Dispatchers.Default }
}
