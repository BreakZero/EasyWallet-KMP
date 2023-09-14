package com.easy.wallet.data.di

import com.easy.wallet.data.global.HDWalletInstant
import com.easy.wallet.data.multiwallet.MultiWalletRepository
import com.easy.wallet.data.news.NewsApi
import com.easy.wallet.data.news.NewsRepository
import com.easy.wallet.data.nft.opensea.OpenseaApi
import com.easy.wallet.data.nft.opensea.OpenseaNftRepository
import com.easy.wallet.data.platform.httpClient
import com.easy.wallet.data.token.TokenLocalDatasource
import com.easy.wallet.data.token.TokenRemoteDatasource
import com.easy.wallet.data.token.TokenRepository
import com.easy.wallet.database.di.databaseModule
import com.easy.wallet.datastore.DatabaseKeyStorage
import com.easy.wallet.datastore.UserPasswordStorage
import com.easy.wallet.datastore.di.userDefaultModule
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.client.request.header
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.module

private const val OPENSEA = "opensea"
private const val BLOCK_CHAIR = "blockchair"

val dataModule = module {
    includes(userDefaultModule())
    includes(databaseModule)

    singleOf(::UserPasswordStorage)
    singleOf(::DatabaseKeyStorage)
    single {
        MultiWalletRepository(get())
    }

    single(qualifier = named(name = BLOCK_CHAIR)) {
        httpClient {
            defaultRequest {
                url("https://api.blockchair.com/")
                header("accept", "application/json")
            }
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
        }
    }
    single(qualifier = named(name = OPENSEA)) {
        httpClient {
            defaultRequest {
                url("https://api.opensea.io/v2/")
                header("accept", "application/json")
                // TODO move to backend
                header("X-API-KEY", "")
            }
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
        }
    }
    single {
        OpenseaApi(get(qualifier = named(OPENSEA)))
    }
    single {
        NewsApi(get(qualifier = named(BLOCK_CHAIR)))
    }
    singleOf(::OpenseaNftRepository)
    singleOf(::NewsRepository)

    singleOf(::TokenRemoteDatasource)
    single {
        TokenLocalDatasource(get())
    }
    singleOf(::TokenRepository)
    singleOf(::HDWalletInstant)
}
