package com.easy.wallet.network.source.evm_rpc

import com.easy.wallet.network.source.evm_rpc.dto.BaseRpcResponseDto
import com.easy.wallet.network.source.evm_rpc.parameter.EmptyParameter
import com.easy.wallet.network.tryPost
import io.ktor.client.HttpClient
import io.ktor.client.request.setBody

class EvmJsonRpcApiImpl internal constructor(
    private val httpClient: HttpClient
) : JsonRpcApi {
    override suspend fun estimateGas(
        from: String,
        to: String,
        gas: Long?,
        gasPrice: Long?,
        maxPriorityFeePerGas: Long?,
        maxFeePerGas: Long?,
        value: Long?,
        data: String?
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun feeHistory(blockCount: Int, percentiles: List<Int>) {
        TODO("Not yet implemented")
    }

    override suspend fun gasPrice(): String {
        val result = httpClient.tryPost<BaseRpcResponseDto<String>>("", isThrows = true) {
            setBody(
                EmptyParameter(
                    jsonrpc = "2.0",
                    id = 1,
                    method = "eth_gasPrice",
                    params = emptyList()
                )
            )
        }
        return result?.result.orEmpty()
    }

    override suspend fun getTransactionCount(account: String) {
        TODO("Not yet implemented")
    }

    override suspend fun getBalance(account: String) {
        TODO("Not yet implemented")
    }

    override suspend fun sendRawTransaction(data: String) {
        TODO("Not yet implemented")
    }

    override suspend fun methodCall(
        from: String,
        to: String,
        gas: Long?,
        gasPrice: Long?,
        value: Long?,
        data: String
    ) {
        TODO("Not yet implemented")
    }
}


