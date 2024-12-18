package com.easy.wallet.network.source.opensea

import com.easy.wallet.network.source.opensea.dto.NftListDto
import com.easy.wallet.network.tryGet
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter

class OpenseaApiController internal constructor(
  private val httpClient: HttpClient
) : OpenseaApi {
  override suspend fun retrieveNFTsByAccount(
    account: String,
    chain: String,
    limit: Int
  ): List<Any>? = httpClient
    .tryGet<NftListDto>("chain/$chain/account/$account/nfts") {
      parameter("limit", limit)
    }?.nfts
}
