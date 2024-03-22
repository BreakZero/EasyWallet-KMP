package com.easy.wallet.network.source.evm_rpc

interface JsonRpcApi {

    suspend fun estimateGas(
        from: String,
        to: String,
        gas: Long?,
        gasPrice: Long?,
        maxPriorityFeePerGas: Long?,
        maxFeePerGas: Long?,
        value: Long?,
        data: String?
    )

    suspend fun feeHistory(blockCount: Int, percentiles: List<Int>)
    suspend fun gasPrice()
    suspend fun getTransactionCount(account: String)
    suspend fun getBalance(account: String)

    suspend fun sendRawTransaction(data: String)

    suspend fun methodCall(
        from: String,
        to: String,
        gas: Long?,
        gasPrice: Long?,
        value: Long?,
        data: String
    )
}
