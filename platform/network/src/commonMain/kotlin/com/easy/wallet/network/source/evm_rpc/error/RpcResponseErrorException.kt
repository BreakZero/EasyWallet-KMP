package com.easy.wallet.network.source.evm_rpc.error

import io.ktor.utils.io.errors.IOException

class RpcResponseErrorException(message: String) : IOException(message)
