package com.easy.wallet.database.di

import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

expect fun databaseModule(): Module
