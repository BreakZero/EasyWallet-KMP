package com.easy.wallet.network.source.opensea

import com.easy.wallet.network.source.opensea.dto.NftListDto

interface OpenseaApi {
    suspend fun retrieveNFTsByAccount(account: String, chain: String, limit: Int): NftListDto
}
