package com.easy.wallet.network.source.evm_rpc.error

import io.ktor.utils.io.errors.IOException

class NetworkErrorException(message: String) : IOException(message)
