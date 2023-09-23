package com.easy.wallet.network.source.etherscan

import io.ktor.client.HttpClient

class EtherscanDataSource(
    private val httpClient: HttpClient
) : EtherscanApi
