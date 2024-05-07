package com.easy.wallet.network.source.evm_rpc

interface JsonRpcApi {

    suspend fun estimateGas(
        rpcUrl: String,
        from: String,
        to: String,
        value: Long?,
        data: String?
    ): String

    suspend fun feeHistory(rpcUrl: String,blockCount: Int, percentiles: List<Int>): Pair<String, String>
    suspend fun gasPrice(rpcUrl: String,): String
    suspend fun getTransactionCount(rpcUrl: String,account: String):String

    suspend fun getBalance(rpcUrl: String,account: String, contract: String?): String

    suspend fun sendRawTransaction(rpcUrl: String,data: String): String

    suspend fun methodCall(
        rpcUrl: String,
        from: String,
        to: String,
        gas: Long?,
        gasPrice: Long?,
        value: Long?,
        data: String
    )
}
