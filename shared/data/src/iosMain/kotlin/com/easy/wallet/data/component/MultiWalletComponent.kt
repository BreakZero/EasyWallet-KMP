package com.easy.wallet.data.component

import com.easy.wallet.core.common.wrap
import com.easy.wallet.data.multiwallet.MultiWalletRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.experimental.ExperimentalObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName("MultiWalletComponent")
class MultiWalletComponent : KoinComponent {
    private val multiWalletRepository: MultiWalletRepository by inject()

    fun forActivatedOne() = multiWalletRepository.forActivatedOne().wrap()
}
