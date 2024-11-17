package com.easy.wallet.network

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttp
import io.ktor.client.plugins.HttpRequestRetry
import io.ktor.client.plugins.UserAgent

internal actual fun httpClient(config: HttpClientConfig<*>.() -> Unit) = HttpClient(OkHttp) {
  config(this)
  install(UserAgent) {
    agent = "Android"
  }
  install(HttpRequestRetry) {
    retryOnServerErrors(maxRetries = 3)
    exponentialDelay()
  }
}
