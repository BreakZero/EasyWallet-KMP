package com.easy.wallet.network.source.blockchair.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class BlockChairBaseResponse<T>(
    @SerialName("data")
    val data: T,
    @SerialName("context")
    val context: BlockChairContextDto
)
