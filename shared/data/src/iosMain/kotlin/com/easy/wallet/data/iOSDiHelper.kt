package com.easy.wallet.data

import com.easy.wallet.data.di.dataModule
import org.koin.core.context.startKoin

fun iOSKoinInit() {
    startKoin {
        modules(dataModule)
    }
}
