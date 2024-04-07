package com.easy.wallet.network

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.utils.io.errors.IOException

internal expect fun httpClient(config: HttpClientConfig<*>.() -> Unit = {}): HttpClient

internal suspend inline fun <reified T> HttpClient.tryGet(
    urlString: String,
    isThrows: Boolean = false,
    block: HttpRequestBuilder.() -> Unit = {}
): T? {
    return try {
        get(urlString, block).body<T>()
    } catch (exception: ResponseException) {
        exception.debugLogging()
        if (isThrows) throw exception else null
    } catch (ioException: IOException) {
        if (isThrows) throw ioException else null
    }
}

internal suspend inline fun <reified T> HttpClient.tryPost(
    urlString: String,
    isThrows: Boolean = false,
    block: HttpRequestBuilder.() -> Unit = {}
): T? {
    return try {
        post(urlString, block).body<T>()
    } catch (exception: ResponseException) {
        exception.debugLogging()
        if (isThrows) throw exception else null
    } catch (ioException: IOException) {
        ioException.printStackTrace()
        if (isThrows) throw ioException else null
    }
}

private suspend fun ResponseException.debugLogging() {
    when (this) {
        is RedirectResponseException -> {
            // Status codes 3XX
            val errorString = response.body<String>()
            println("===== $errorString")
        }

        is ClientRequestException -> {
            // status codes 4XX
            println("===== status codes 4XX")
        }

        is ServerResponseException -> {
            // status codes 5XX
            println("===== status codes 5XX")
        }
    }
}
