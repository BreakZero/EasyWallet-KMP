package com.easy.wallet.network.source.opensea

import com.easy.wallet.network.doGetWithCatch
import com.easy.wallet.network.source.opensea.dto.NftListDto
import io.ktor.client.HttpClient
import io.ktor.client.request.parameter

class OpenseaDataSource internal constructor(
    private val httpClient: HttpClient
) : OpenseaApi {
    override suspend fun retrieveNFTsByAccount(
        account: String,
        chain: String,
        limit: Int
    ): NftListDto? {
        return httpClient.doGetWithCatch<NftListDto>("chain/$chain/account/$account/nfts") {
            parameter("limit", limit)
        }
    }
}
