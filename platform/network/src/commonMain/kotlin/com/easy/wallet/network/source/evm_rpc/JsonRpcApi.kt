package com.easy.wallet.network.source.evm_rpc

interface JsonRpcApi {

    suspend fun estimateGas(
        from: String,
        to: String,
        value: Long?,
        data: String?
    ): String

    suspend fun feeHistory(blockCount: Int, percentiles: List<Int>): Pair<String, String>
    suspend fun gasPrice(): String
    suspend fun getTransactionCount(account: String):String
    suspend fun getBalance(account: String)

    suspend fun sendRawTransaction(data: String): String

    suspend fun methodCall(
        from: String,
        to: String,
        gas: Long?,
        gasPrice: Long?,
        value: Long?,
        data: String
    )
}
