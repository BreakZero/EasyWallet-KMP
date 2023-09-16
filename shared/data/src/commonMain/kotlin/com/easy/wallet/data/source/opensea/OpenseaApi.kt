package com.easy.wallet.data.source.opensea

import com.easy.wallet.data.source.opensea.model.NftListDto

interface OpenseaApi {
    suspend fun retrieveNFTsByAccount(account: String, chain: String, limit: Int): NftListDto
}
