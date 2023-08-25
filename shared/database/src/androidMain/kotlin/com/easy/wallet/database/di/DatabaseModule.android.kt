package com.easy.wallet.database.di

import com.easy.wallet.database.platform.DatabaseDriverFactory
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual fun databaseModule() = module {
    singleOf(::DatabaseDriverFactory)
}