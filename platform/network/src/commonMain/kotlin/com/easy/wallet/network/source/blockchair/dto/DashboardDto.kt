package com.easy.wallet.network.source.blockchair.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class DashboardRootResponse(
    @SerialName("data")
    val data: Map<String, DashboardResponse>,
    @SerialName("context")
    val context: BlockChairContextDto
)

@Serializable
data class DashboardResponse(
    @SerialName("address")
    val dashboardInfo: DashboardDto,
    @SerialName("layer_2")
    val layer2Dto: Layer2Dto?
)

@Serializable
data class Layer2Dto(
    @SerialName("erc_20")
    val ethTokens: List<TokenInfoDto>
)

@Serializable
data class DashboardDto(
    @SerialName("balance")
    val balance: String,
    @SerialName("balance_usd")
    val balanceUsd: Double,
    @SerialName("call_count")
    val callCount: Int,
    @SerialName("fees_approximate")
    val feesApproximate: String,
    @SerialName("fees_usd")
    val feesUsd: Int,
    @SerialName("nonce")
    val nonce: Int?,
    @SerialName("received_approximate")
    val receivedApproximate: String,
    @SerialName("received_usd")
    val receivedUsd: Int,
    @SerialName("receiving_call_count")
    val receivingCallCount: Int,
    @SerialName("spending_call_count")
    val spendingCallCount: Int,
    @SerialName("spent_approximate")
    val spentApproximate: String,
    @SerialName("spent_usd")
    val spentUsd: Int,
    @SerialName("transaction_count")
    val transactionCount: Int
)
