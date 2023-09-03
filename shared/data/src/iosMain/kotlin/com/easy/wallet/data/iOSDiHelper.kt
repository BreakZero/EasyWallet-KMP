package com.easy.wallet.data

import com.easy.wallet.data.di.dataModule
import org.koin.core.context.startKoin
import kotlin.experimental.ExperimentalObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName("initKoin")
fun iOSKoinInit() {
    startKoin {
        modules(dataModule)
    }
}
