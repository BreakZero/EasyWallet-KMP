package com.easy.wallet.shared.model.fees

sealed interface FeeModel {
    val feeLevel: FeeLevel
}

data class EthereumFee(
    override val feeLevel: FeeLevel,
    val gas: String,
    val maxFeePerGas: String,
    val priorityFeeGas: String,
): FeeModel
