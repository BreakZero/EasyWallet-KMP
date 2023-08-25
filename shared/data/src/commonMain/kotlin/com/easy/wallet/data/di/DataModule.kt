package com.easy.wallet.data.di

import com.easy.wallet.data.nft.opensea.OpenseaApi
import com.easy.wallet.data.nft.opensea.OpenseaNftRepository
import com.easy.wallet.database.di.databaseModule
import com.easy.wallet.datastore.UserPasswordStorage
import com.easy.wallet.datastore.di.userDefaultModule
import org.koin.dsl.module

val dataModule = module {
    includes(userDefaultModule())
    includes(databaseModule())
    single {
        OpenseaApi()
    }
    single {
        OpenseaNftRepository(get())
    }
    single {
        UserPasswordStorage()
    }
}