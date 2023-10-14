package com.easy.wallet.network.source.okx

import com.easy.wallet.network.source.okx.dto.InstrumentDto
import io.ktor.client.HttpClient

class OkxDataSource internal constructor(
    private val httpClient: HttpClient
) : OkxApi {
    override suspend fun instruments(): List<InstrumentDto> {
        TODO("Not yet implemented")
    }
}
