package com.easy.wallet.data.di

import com.easy.wallet.data.nft.opensea.OpenseaApi
import com.easy.wallet.data.nft.opensea.OpenseaNftRepository
import org.koin.dsl.module

val dataModule = module {
    single {
        OpenseaApi()
    }
    single {
        OpenseaNftRepository(get())
    }
}