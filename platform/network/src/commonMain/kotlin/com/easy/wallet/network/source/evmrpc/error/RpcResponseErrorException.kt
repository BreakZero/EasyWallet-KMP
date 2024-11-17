package com.easy.wallet.network.source.evmrpc.error

import io.ktor.utils.io.errors.IOException

class RpcResponseErrorException(
  message: String
) : IOException(message)
