package com.easy.wallet.network.source.evmrpc

import co.touchlab.kermit.Logger
import com.easy.wallet.core.commom.cleanHexPrefix
import com.easy.wallet.network.source.evmrpc.dto.BaseRpcResponseDto
import com.easy.wallet.network.source.evmrpc.dto.FeeHistoryDto
import com.easy.wallet.network.source.evmrpc.dto.RpcErrorResponse
import com.easy.wallet.network.source.evmrpc.error.RpcResponseErrorException
import com.easy.wallet.network.source.evmrpc.parameter.Parameter
import com.easy.wallet.network.source.evmrpc.parameter.RpcRequestBody
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.HttpResponse
import io.ktor.client.statement.bodyAsText
import kotlinx.serialization.json.Json

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
    rpcUrl: String,
    from: String,
    to: String,
    value: Long?,
    data: String?
  ): String {
    val body = defaultBody.copy(
      method = "eth_estimateGas",
      params = listOf(
        Parameter.CallParameter(
          from = from,
          to = to,
          data = data.orEmpty()
        )
      )
    )
    val response = httpClient
      .post(rpcUrl) {
        setBody(body)
      }.also {
        validateResponse(it)
      }.body<BaseRpcResponseDto<String>>()
    return response.result
  }

  override suspend fun feeHistory(
    rpcUrl: String,
    blockCount: Int,
    percentiles: List<Int>
  ): Pair<String, String> {
    val body = defaultBody.copy(
      method = "eth_feeHistory",
      params = listOf(
        Parameter.StringParameter(blockCount.toString(16)),
        Parameter.StringParameter("latest"),
        Parameter.IntListParameter(percentiles)
      )
    )
    val response = httpClient
      .post(rpcUrl) {
        setBody(body)
      }.also {
        validateResponse(it)
      }.body<BaseRpcResponseDto<FeeHistoryDto>>()
    return calculatedFee(
      response.result
    )
  }

  @OptIn(ExperimentalStdlibApi::class)
  private fun calculatedFee(feeHistory: FeeHistoryDto): Pair<String, String> {
    val baseFees = feeHistory.baseFeePerGas
    val aveBaseFee = if (baseFees.isEmpty()) {
      0L
    } else {
      feeHistory.baseFeePerGas
        .map { it.substring(2).hexToLong() }
        .average()
        .toLong()
    }
    val maxFee = (aveBaseFee * 2) + feeHistory.reward
      .map {
        it.map { it.substring(2).hexToLong() }.average()
      }.average()
      .toLong()
    return Pair(maxFee.times(2).toString(16), maxFee.toString(16))
  }

  override suspend fun gasPrice(rpcUrl: String): String {
    val response = httpClient
      .post(rpcUrl) {
        setBody(defaultBody.copy(method = "eth_gasPrice"))
      }.also {
        validateResponse(it)
      }.body<BaseRpcResponseDto<String>>()
    return response.result
  }

  override suspend fun getTransactionCount(rpcUrl: String, account: String): String {
    val response = httpClient
      .post(rpcUrl) {
        setBody(
          defaultBody.copy(
            method = "eth_getTransactionCount",
            params = listOf(
              Parameter.StringParameter(account),
              Parameter.StringParameter("latest")
            )
          )
        )
      }.also {
        validateResponse(it)
      }.body<BaseRpcResponseDto<String>>()
    return response.result
  }

  override suspend fun getBalance(
    rpcUrl: String,
    account: String,
    contract: String?
  ): String {
    val finalBody = if (contract.isNullOrBlank()) {
      defaultBody.copy(
        method = "eth_getBalance",
        params = listOf(
          Parameter.StringParameter(account),
          Parameter.StringParameter("latest")
        )
      )
    } else {
      /**
       * balanceOf input data format is `0x70a08231000000000000000000000000${address(without hex prefix)}`
       */
      defaultBody.copy(
        method = "eth_call",
        params = listOf(
          Parameter.CallParameter(
            from = account,
            to = contract,
            data = "0x70a08231000000000000000000000000${account.cleanHexPrefix()}"
          ),
          Parameter.StringParameter("latest")
        )
      )
    }
    val balanceRes = httpClient
      .post(rpcUrl) {
        setBody(finalBody)
      }.also {
        validateResponse(it)
      }.body<BaseRpcResponseDto<String>>()

    return balanceRes.result
  }

  override suspend fun sendRawTransaction(rpcUrl: String, data: String): String {
    val hashResponse = httpClient
      .post(rpcUrl) {
        setBody(
          defaultBody.copy(
            method = "eth_sendRawTransaction",
            params = listOf(Parameter.StringParameter(data))
          )
        )
      }.also {
        validateResponse(it)
      }.body<BaseRpcResponseDto<String>>()
    return hashResponse.result
  }

  override suspend fun methodCall(
    rpcUrl: String,
    from: String,
    to: String,
    gas: Long?,
    gasPrice: Long?,
    value: Long?,
    data: String
  ) {
  }

  companion object {
    private const val TAG = "RPC_API_INFO"
  }

  private suspend fun validateResponse(response: HttpResponse) {
    val text = response.bodyAsText()
    val message = try {
      JSON.decodeFromString<RpcErrorResponse>(text).error?.message
    } catch (e: Throwable) {
      null
    }
    if (!message.isNullOrBlank()) {
      Logger.e(TAG) { message }
      throw RpcResponseErrorException(message)
    }
  }
}

internal val JSON = Json {
  prettyPrint = true
  ignoreUnknownKeys = true
  isLenient = true
}
