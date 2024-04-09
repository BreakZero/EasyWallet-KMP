package com.easy.wallet.network.source.evm_rpc.error

import io.ktor.utils.io.errors.IOException

class RpcErrorException(message: String) : IOException(message)
