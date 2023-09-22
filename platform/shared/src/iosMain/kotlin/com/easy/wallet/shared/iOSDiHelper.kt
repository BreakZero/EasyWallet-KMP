package com.easy.wallet.shared

import com.easy.wallet.shared.di.sharedModule
import org.koin.core.context.startKoin
import kotlin.experimental.ExperimentalObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName("initKoin")
fun iOSKoinInit() {
    startKoin {
        modules(sharedModule)
    }
}
