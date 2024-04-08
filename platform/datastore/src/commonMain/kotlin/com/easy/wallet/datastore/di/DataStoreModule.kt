package com.easy.wallet.datastore.di

import com.easy.wallet.datastore.UserPasswordStorage
import com.easy.wallet.datastore.DatabaseKeyStorage
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

expect fun userDefaultModule(): Module

fun storageModule() = module {
    singleOf(::UserPasswordStorage)
    singleOf(::DatabaseKeyStorage)
}
