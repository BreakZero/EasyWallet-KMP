package com.easy.wallet.network.di

import com.easy.wallet.network.httpClient
import com.easy.wallet.network.key.BuildKonfig
import com.easy.wallet.network.source.blockchair.BlockchairApi
import com.easy.wallet.network.source.blockchair.BlockchairApiController
import com.easy.wallet.network.source.coingecko.CoinGeckoApi
import com.easy.wallet.network.source.coingecko.CoinGeckoApiController
import com.easy.wallet.network.source.etherscan.EtherscanApi
import com.easy.wallet.network.source.etherscan.EtherscanApiController
import com.easy.wallet.network.source.evm_rpc.EvmJsonRpcApiImpl
import com.easy.wallet.network.source.evm_rpc.JsonRpcApi
import com.easy.wallet.network.source.evm_rpc.parameter.Parameter
import com.easy.wallet.network.source.okx.OKXWebSocketManager
import com.easy.wallet.network.source.opensea.OpenseaApi
import com.easy.wallet.network.source.opensea.OpenseaApiController
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
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json
import kotlinx.serialization.modules.SerializersModule
import kotlinx.serialization.modules.polymorphic
import org.koin.core.qualifier.named
import org.koin.dsl.module

enum class SourceQualifier {
    OPENSEA, BLOCK_CHAIR, ETHER_SCAN, OKX_WEBSOCKETS, COINGECKO, EVM_RPC
}

private fun httpClientWithDefault(serializersModule: SerializersModule? = null, special: HttpClientConfig<*>.() -> Unit = {}): HttpClient {
    return httpClient {
        install(ContentNegotiation) {
            json(
                Json {
                    prettyPrint = true
                    isLenient = true
                    ignoreUnknownKeys = true
                    allowStructuredMapKeys = true
                    serializersModule?.let {
                        this.serializersModule = it
                    }
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
                header("Content-Type", "application/json")
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
                header("Content-Type", "application/json")
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
//                    host = "api-sepolia.etherscan.io"
                    path("api/")
                    parameters.append("apikey", BuildKonfig.ETHERSCAN_KEY)
                }
                header("Content-Type", "application/json")
            }
        }
    }
    single(qualifier = named(name = SourceQualifier.EVM_RPC.name)) {
        httpClientWithDefault(
            serializersModule = SerializersModule {
                polymorphic(List::class) {
                    ListSerializer(Parameter.serializer())
                }
                polymorphic(Parameter::class) {
                    subclass(Parameter.CallParameter::class, Parameter.CallParameter.serializer())
                    subclass(Parameter.StringParameter::class, Parameter.StringParameter.serializer())
                    subclass(Parameter.IntListParameter::class, Parameter.IntListParameter.serializer())
                }
            }
        ) {
            defaultRequest {
                url {
                    protocol = URLProtocol.HTTPS
                    host = "mainnet.infura.io"
//                    host = "sepolia.infura.io"
                    path("v3/${BuildKonfig.INFURA_KEY}")
                }
                header("Content-Type", "application/json")
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
                header("Content-Type", "application/json")
            }
        }
    }
    single(qualifier = named(name = SourceQualifier.OKX_WEBSOCKETS.name)) { webSocketClient() }

    single<BlockchairApi> { BlockchairApiController(get(qualifier = named(SourceQualifier.BLOCK_CHAIR.name))) }
    single<OpenseaApi> { OpenseaApiController(get(qualifier = named(SourceQualifier.OPENSEA.name))) }
    single<EtherscanApi> { EtherscanApiController(get(qualifier = named(SourceQualifier.ETHER_SCAN.name))) }
    single<CoinGeckoApi> { CoinGeckoApiController(get(qualifier = named(SourceQualifier.COINGECKO.name))) }
    single<JsonRpcApi> { EvmJsonRpcApiImpl(get(qualifier = named(SourceQualifier.EVM_RPC.name))) }

    single { OKXWebSocketManager(get(qualifier = named(SourceQualifier.OKX_WEBSOCKETS.name))) }
}
