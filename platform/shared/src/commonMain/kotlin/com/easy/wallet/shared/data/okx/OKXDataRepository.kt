package com.easy.wallet.shared.data.okx

import com.easy.wallet.network.source.okx.OKXWebSocketManager

class OKXDataRepository internal constructor(
    private val okxWebSocketManager: OKXWebSocketManager
) {
    suspend fun connect(path: String, callback: suspend () -> Unit) {
        okxWebSocketManager.connect(path, callback)
    }

//    suspend fun subscribe(
//        args: List<OptionArg>,
//        onReceive: (String) -> Unit
//    ) {
//        okxWebSocketManager.subscribe(args, onReceive)
//    }
//
//    suspend fun unsubscribe(
//        args: List<OptionArg>
//    ) {
//        okxWebSocketManager.unsubscribe(args)
//    }
}
