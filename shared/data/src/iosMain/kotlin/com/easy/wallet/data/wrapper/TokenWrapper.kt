package com.easy.wallet.data.wrapper

import com.easy.wallet.core.common.wrap
import com.easy.wallet.data.token.TokenRepository
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.experimental.ExperimentalObjCName

@OptIn(ExperimentalObjCName::class)
class TokenWrapper : KoinComponent {
    private val tokenRepository: TokenRepository by inject()

    @ObjCName("loadTokens")
    fun loadTokens() = suspend { tokenRepository.loadTokens() }.wrap()
}