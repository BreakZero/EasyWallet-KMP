package com.easy.wallet.data.nft.opensea

import com.easy.wallet.data.nft.model.NftListDto
import com.easy.wallet.data.platform.httpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

class OpenseaApi {
    private val client = httpClient {
        defaultRequest {
            url("https://api.opensea.io/v2/")
            header("accept", "application/json")
            // TODO move to backend
            header("X-API-KEY", "")
        }
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.BODY
        }
    }

    suspend fun retrieveNFTsByAccount(
        account: String,
        chain: String,
        limit: Int
    ): NftListDto {
        val nfts: NftListDto = client.get("chain/$chain/account/$account/nfts?limit=$limit").body()
        return nfts
    }
}