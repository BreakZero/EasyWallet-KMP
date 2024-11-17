package com.easy.wallet.network.source.etherscan.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class EtherTransactionsResponseDto(
  @SerialName("message")
  val message: String,
  @SerialName("result")
  val result: List<EtherTransactionDto>,
  @SerialName("status")
  val status: String
)

@Serializable
internal data class EtherTransactionDto(
  @SerialName("blockHash")
  val blockHash: String,
  @SerialName("blockNumber")
  val blockNumber: String,
  @SerialName("confirmations")
  val confirmations: String,
  @SerialName("contractAddress")
  val contractAddress: String,
  @SerialName("cumulativeGasUsed")
  val cumulativeGasUsed: String,
  @SerialName("from")
  val from: String,
  @SerialName("gas")
  val gas: String,
  @SerialName("gasPrice")
  val gasPrice: String,
  @SerialName("gasUsed")
  val gasUsed: String,
  @SerialName("hash")
  val hash: String,
  @SerialName("input")
  val input: String,
  @SerialName("nonce")
  val nonce: String,
  @SerialName("timeStamp")
  val timeStamp: String,
  @SerialName("to")
  val to: String,
  @SerialName("transactionIndex")
  val transactionIndex: String,
  @SerialName("value")
  val value: String,
  @SerialName("isError")
  val isError: String? = null,
  @SerialName("methodId")
  val methodId: String? = null,
  @SerialName("functionName")
  val functionName: String? = null,
)
