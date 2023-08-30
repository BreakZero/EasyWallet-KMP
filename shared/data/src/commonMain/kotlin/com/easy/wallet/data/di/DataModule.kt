package com.easy.wallet.data.di

import com.easy.wallet.data.hdwallet.HDWalletInMemory
import com.easy.wallet.data.multiwallet.MultiWalletRepository
import com.easy.wallet.data.nft.opensea.OpenseaApi
import com.easy.wallet.data.nft.opensea.OpenseaNftRepository
import com.easy.wallet.database.di.databaseModule
import com.easy.wallet.datastore.DatabaseKeyStorage
import com.easy.wallet.datastore.UserPasswordStorage
import com.easy.wallet.datastore.di.userDefaultModule
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val dataModule = module {
    includes(userDefaultModule())
    includes(databaseModule())

    singleOf(::OpenseaApi)
    singleOf(::OpenseaNftRepository)
    singleOf(::UserPasswordStorage)
    singleOf(::DatabaseKeyStorage)
    singleOf(::HDWalletInMemory)
    single {
        MultiWalletRepository(get(), get())
    }
}