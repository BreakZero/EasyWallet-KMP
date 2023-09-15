package com.easy.wallet.data.wrapper

import com.easy.wallet.core.common.wrap
import com.easy.wallet.data.repository.SupportedTokenRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.experimental.ExperimentalObjCName

@OptIn(ExperimentalObjCName::class)
@ObjCName("TokenWrapper")
class TokenWrapper : KoinComponent {
    private val supportedTokenRepository: SupportedTokenRepository by inject()
    @ObjCName("supportedTokens")
    fun supportedTokens() = supportedTokenRepository.allSupportedTokens().wrap()
}
