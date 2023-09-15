package com.easy.wallet.data.di

import com.easy.wallet.data.global.HDWalletInstant
import com.easy.wallet.data.multiwallet.MultiWalletRepository
import com.easy.wallet.data.news.NewsRepository
import com.easy.wallet.data.platform.httpClient
import com.easy.wallet.data.repository.BitcoinRepository
import com.easy.wallet.data.repository.EthereumRepository
import com.easy.wallet.data.repository.SupportedTokenRepository
import com.easy.wallet.data.repository.TokenRepository
import com.easy.wallet.data.source.blockchair.BlockchairApi
import com.easy.wallet.data.source.blockchair.BlockchairDataSource
import com.easy.wallet.data.source.opensea.OpenseaApi
import com.easy.wallet.data.source.opensea.OpenseaDataSource
import com.easy.wallet.data.usecase.DashboardUseCase
import com.easy.wallet.database.di.databaseModule
import com.easy.wallet.datastore.DatabaseKeyStorage
import com.easy.wallet.datastore.UserPasswordStorage
import com.easy.wallet.datastore.di.userDefaultModule
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
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

val dataModule = module {
    includes(userDefaultModule())
    includes(databaseModule)

    singleOf(::UserPasswordStorage)
    singleOf(::DatabaseKeyStorage)
    single {
        MultiWalletRepository(get())
    }

    single(qualifier = named(name = BLOCK_CHAIR)) {
        httpClientWithDefault {
            defaultRequest {
                url("https://api.blockchair.com/")
                header("accept", "application/json")
            }
        }
    }
    single(qualifier = named(name = OPENSEA)) {
        httpClientWithDefault {
            defaultRequest {
                url("https://api.opensea.io/v2/")
                header("accept", "application/json")
                // TODO move to backend, delegate forward
                header("X-API-KEY", "")
            }
        }
    }
    single<BlockchairApi> { BlockchairDataSource(get(qualifier = named(BLOCK_CHAIR))) }
    single<OpenseaApi> { OpenseaDataSource(get(qualifier = named(OPENSEA))) }

    singleOf(::NewsRepository)
    singleOf(::SupportedTokenRepository)
    singleOf(::HDWalletInstant)

    single<TokenRepository>(named("Bitcoin")) { BitcoinRepository() }
    single<TokenRepository>(named("Ethereum")) { EthereumRepository() }

    single { DashboardUseCase(get(), get(named("Ethereum")), get(named("Bitcoin"))) }
}
