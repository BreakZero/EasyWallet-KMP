package com.easy.wallet.network.source.okx

import com.easy.wallet.network.source.okx.dto.InstrumentDto

interface OkxApi {
    suspend fun instruments(): List<InstrumentDto>
}
