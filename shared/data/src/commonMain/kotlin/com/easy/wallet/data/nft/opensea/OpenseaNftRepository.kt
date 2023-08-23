package com.easy.wallet.data.nft.opensea

import com.easy.wallet.data.nft.model.NftListDto

class OpenseaNftRepository(
    private val openseaApi: OpenseaApi
) {
    suspend fun retrieveNFTsByAccount(
        account: String,
        chain: String,
        limit: Int
    ): NftListDto {
        return openseaApi.retrieveNFTsByAccount(account, chain, limit)
    }
}