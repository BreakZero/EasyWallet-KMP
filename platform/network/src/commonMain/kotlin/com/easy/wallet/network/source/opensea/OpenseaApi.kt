package com.easy.wallet.network.source.opensea

import com.easy.wallet.network.source.opensea.model.NftListDto

interface OpenseaApi {
    suspend fun retrieveNFTsByAccount(account: String, chain: String, limit: Int): NftListDto
}
