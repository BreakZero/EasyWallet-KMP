package com.easy.wallet.network.source.evm_rpc

import com.easy.wallet.network.source.evm_rpc.dto.BaseRpcResponseDto
import com.easy.wallet.network.source.evm_rpc.dto.FeeHistoryDto
import com.easy.wallet.network.source.evm_rpc.parameter.Parameter
import com.easy.wallet.network.source.evm_rpc.parameter.RpcRequestBody
import com.easy.wallet.network.tryPost
import io.ktor.client.HttpClient
import io.ktor.client.request.setBody

class EvmJsonRpcApiImpl internal constructor(
    private val httpClient: HttpClient
) : JsonRpcApi {
    private val defaultBody = RpcRequestBody(
        jsonrpc = "2.0",
        id = 1,
        method = "",
        params = emptyList()
    )

    override suspend fun estimateGas(
        from: String,
        to: String,
        value: Long?,
        data: String?
    ): String {
        val body = defaultBody.copy(
            method = "eth_estimateGas", params = listOf(
                Parameter.CallParameter(
                    from = from,
                    to = to,
                    data = data.orEmpty()
                )
            )
        )
        val response = httpClient.tryPost<BaseRpcResponseDto<String>> {
            setBody(body)
        }
        return response.result
    }

    override suspend fun feeHistory(blockCount: Int, percentiles: List<Int>): Pair<String, String> {
        val body = defaultBody.copy(
            method = "eth_feeHistory",
            params = listOf(
                Parameter.StringParameter(blockCount.toString(16)),
                Parameter.StringParameter("latest"),
                Parameter.IntListParameter(percentiles)
            )
        )
        val response = httpClient.tryPost<BaseRpcResponseDto<FeeHistoryDto>> {
            setBody(body)
        }
        return calculatedFee(response.result)

    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun calculatedFee(feeHistory: FeeHistoryDto): Pair<String, String> {
        val baseFees = feeHistory.baseFeePerGas
        val aveBaseFee = if (baseFees.isEmpty()) {
            0L
        } else {
            feeHistory.baseFeePerGas.map { it.substring(2).hexToLong() }.average().toLong()
        }
        val maxFee = (aveBaseFee * 2) + feeHistory.reward.map {
            it.map { it.substring(2).hexToLong() }.average()
        }.average().toLong()
        return Pair(maxFee.toString(16), aveBaseFee.toString(16))
    }

    override suspend fun gasPrice(): String {
        val result = httpClient.tryPost<BaseRpcResponseDto<String>> {
            setBody(defaultBody.copy(method = "eth_gasPrice"))
        }
        return result.result
    }

    override suspend fun getTransactionCount(account: String): String {
        val response = httpClient.tryPost<BaseRpcResponseDto<String>> {
            setBody(
                defaultBody.copy(
                    method = "eth_getTransactionCount",
                    params = listOf(
                        Parameter.StringParameter(account),
                        Parameter.StringParameter("latest")
                    )
                )
            )
        }
        return response.result
    }

    override suspend fun getBalance(account: String) {
        TODO("Not yet implemented")
    }

    override suspend fun sendRawTransaction(data: String): String {
        val hashResponse = httpClient.tryPost<BaseRpcResponseDto<String>> {
            setBody(
                defaultBody.copy(
                    method = "eth_sendRawTransaction",
                    params = listOf(Parameter.StringParameter(data))
                )
            )
        }
        return hashResponse.result
    }

    override suspend fun methodCall(
        from: String,
        to: String,
        gas: Long?,
        gasPrice: Long?,
        value: Long?,
        data: String
    ) {

    }
}


