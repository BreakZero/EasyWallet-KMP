package com.easy.wallet.network.source.okx


interface OkxApi {
    suspend fun instruments(): List<Any>
}
