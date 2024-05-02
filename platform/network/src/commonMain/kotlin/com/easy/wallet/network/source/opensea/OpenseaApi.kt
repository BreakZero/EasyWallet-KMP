package com.easy.wallet.network.source.opensea

interface OpenseaApi {
    suspend fun retrieveNFTsByAccount(account: String, chain: String, limit: Int): List<Any>?
}
