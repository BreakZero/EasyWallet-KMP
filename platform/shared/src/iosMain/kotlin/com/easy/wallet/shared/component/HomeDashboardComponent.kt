package com.easy.wallet.shared.component

import com.easy.wallet.core.common.wrap
import com.easy.wallet.model.Wallet
import com.easy.wallet.shared.domain.DashboardUseCase
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.experimental.ExperimentalObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName("HomeDashboardComponent")
class HomeDashboardComponent : KoinComponent {
    private val dashboardUseCase: DashboardUseCase by inject()

    fun dashboard(wallet: Wallet) = dashboardUseCase(wallet).wrap()
}
