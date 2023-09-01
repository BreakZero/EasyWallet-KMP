package com.easy.wallet.data.nft.opensea

import com.easy.wallet.data.nft.model.NftListDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get

internal class OpenseaApi(
    private val client: HttpClient
) {
    suspend fun retrieveNFTsByAccount(
        account: String,
        chain: String,
        limit: Int
    ): NftListDto {
        return try {
            val nfts: NftListDto = client.get("chain/$chain/account/$account/nfts?limit=$limit").body()
            nfts
        } catch (e: Exception) {
            NftListDto(emptyList())
        }
    }
}