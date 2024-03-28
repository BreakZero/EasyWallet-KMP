package com.easy.wallet.network.di

import com.easy.wallet.network.httpClient
import com.easy.wallet.network.key.BuildKonfig
import com.easy.wallet.network.source.blockchair.BlockchairApi
import com.easy.wallet.network.source.blockchair.BlockchairDataSource
import com.easy.wallet.network.source.coingecko.CoinGeckoApi
import com.easy.wallet.network.source.coingecko.CoinGeckoDataSource
import com.easy.wallet.network.source.etherscan.EtherscanApi
import com.easy.wallet.network.source.etherscan.EtherscanDataSource
import com.easy.wallet.network.source.okx.OKXWebSocketManager
import com.easy.wallet.network.source.opensea.OpenseaApi
import com.easy.wallet.network.source.opensea.OpenseaDataSource
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.plugins.websocket.WebSockets
import io.ktor.client.request.header
import io.ktor.http.URLProtocol
import io.ktor.http.path
import io.ktor.serialization.kotlinx.KotlinxWebsocketSerializationConverter
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module

enum class SourceQualifier {
    OPENSEA, BLOCK_CHAIR, ETHER_SCAN, OKX_WEBSOCKETS, COINGECKO
}

private fun httpClientWithDefault(special: HttpClientConfig<*>.() -> Unit = {}): HttpClient {
    return httpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                },
            )
        }
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.BODY
        }
        special()
    }
}

private fun webSocketClient(config: HttpClientConfig<*>.() -> Unit = {}): HttpClient {
    return httpClient {
        install(WebSockets) {
            contentConverter = KotlinxWebsocketSerializationConverter(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
        install(Logging) {
            logger = Logger.SIMPLE
            level = LogLevel.BODY
        }
        config()
    }
}

val networkModule = module {
    single(qualifier = named(name = SourceQualifier.BLOCK_CHAIR.name)) {
        httpClientWithDefault {
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "api.blockchair.com"
                    path("/")
                }
                header("accept", "application/json")
            }
        }
    }
    single(qualifier = named(name = SourceQualifier.OPENSEA.name)) {
        httpClientWithDefault {
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "api.opensea.io"
                    path("v2/")
                }
                header("accept", "application/json")
                // TODO move to backend, delegate forward
                header("X-API-KEY", BuildKonfig.OPENSEA_KEY)
            }
        }
    }
    single(qualifier = named(name = SourceQualifier.ETHER_SCAN.name)) {
        httpClientWithDefault {
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "api.etherscan.io"
                    path("api/")
                    parameters.append("apikey", BuildKonfig.ETHERSCAN_KEY)
                }
                header("accept", "application/json")
            }
        }
    }
    single(qualifier = named(name = SourceQualifier.COINGECKO.name)) {
        httpClientWithDefault {
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "api.coingecko.com"
                    path("api/v3/")
                    parameters.append("x_cg_demo_api_key", BuildKonfig.COINGECKO_KEY)
                }
                header("accept", "application/json")
            }
        }
    }
    single(qualifier = named(name = SourceQualifier.OKX_WEBSOCKETS.name)) { webSocketClient() }

    single<BlockchairApi> { BlockchairDataSource(get(qualifier = named(SourceQualifier.BLOCK_CHAIR.name))) }
    single<OpenseaApi> { OpenseaDataSource(get(qualifier = named(SourceQualifier.OPENSEA.name))) }
    single<EtherscanApi> { EtherscanDataSource(get(qualifier = named(SourceQualifier.ETHER_SCAN.name))) }
    single<CoinGeckoApi> { CoinGeckoDataSource(get(qualifier = named(SourceQualifier.COINGECKO.name))) }
    single { OKXWebSocketManager(get(qualifier = named(SourceQualifier.OKX_WEBSOCKETS.name))) }
}
