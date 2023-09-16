package com.easy.wallet.database.di

import com.easy.wallet.database.SharedDatabase
import com.easy.wallet.database.dao.BlockChainDao
import com.easy.wallet.database.dao.BlockChainDaoImpl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

internal expect fun factoryModule(): Module

val databaseModule = module {
    includes(factoryModule())
    singleOf(::SharedDatabase)
    single {
        BlockChainDaoImpl(
            get<SharedDatabase>().database.blockChainQueries,
            dispatcher = Dispatchers.IO,
        )
    } bind BlockChainDao::class
}
