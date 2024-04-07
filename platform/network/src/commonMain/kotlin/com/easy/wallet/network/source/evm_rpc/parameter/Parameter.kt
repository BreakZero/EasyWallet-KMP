package com.easy.wallet.network.source.evm_rpc.parameter

import kotlinx.serialization.Serializable

internal sealed interface Parameter {
    @Serializable
    data class CallParameter(
        val data: String,
        val from: String,
        val to: String
    ) : Parameter

    @Serializable(with = IntListParameterSerializer::class)
    data class IntListParameter(
        val items: List<Int>
    ) : Parameter

    @Serializable(with = StringParameterSerializer::class)
    data class StringParameter(
        val content: String
    ) : Parameter
}
