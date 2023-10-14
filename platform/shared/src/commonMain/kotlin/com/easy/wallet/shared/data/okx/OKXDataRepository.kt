package com.easy.wallet.shared.data.okx

import com.easy.wallet.network.source.okx.OKXWebSocketManager

class OKXDataRepository internal constructor(
    private val okxWebSocketManager: OKXWebSocketManager
) {
    suspend fun connect(path: String, onReceive: (String) -> Unit) {
        okxWebSocketManager.connect(path, onReceive)
    }

    suspend fun subscribe(
        onReceive: (String) -> Unit
    ) {
        okxWebSocketManager.subscribeCandle(onReceive)
    }
}
