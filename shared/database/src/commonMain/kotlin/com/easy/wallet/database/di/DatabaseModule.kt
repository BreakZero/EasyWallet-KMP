package com.easy.wallet.database.di

import com.easy.wallet.database.SharedDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

internal expect fun factoryModule(): Module

val databaseModule = module {
    includes(factoryModule())
    single {
        SharedDatabase(get(), get())
    }
}
