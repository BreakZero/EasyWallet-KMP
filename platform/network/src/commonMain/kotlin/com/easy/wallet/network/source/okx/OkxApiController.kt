package com.easy.wallet.network.source.okx

import io.ktor.client.HttpClient

class OkxApiController internal constructor(
  private val httpClient: HttpClient
) : OkxApi {
  override suspend fun instruments(): List<Any> {
    TODO("Not yet implemented")
  }
}
