package com.easy.wallet.network.source.okx

import com.easy.wallet.network.source.okx.dto.MessageOption
import com.easy.wallet.network.source.okx.dto.OptionArg
import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.sendSerialized
import io.ktor.client.plugins.websocket.wss
import io.ktor.websocket.Frame
import io.ktor.websocket.readText

class OKXWebSocketManager internal constructor(
    private val httpClient: HttpClient
) {
    private var wsSession: DefaultClientWebSocketSession? = null
    suspend fun connect(
        path: String,
        onReceive: (String) -> Unit
    ) {
        httpClient.wss(
            host = "ws.okx.com",
            port = 8443,
            path = path
        ) {
            wsSession = this
            sendSerialized(
                MessageOption(
                    op = "subscribe",
                    args = listOf(
                        OptionArg(channel = "index-candle15m", instId = "BTC-USD")
                    )
                )
            )
            while (true) {
                val message = incoming.receive() as? Frame.Text ?: continue
                onReceive(message.readText())
            }
        }
    }

    suspend fun subscribeCandle(
        onReceive: (String) -> Unit
    ) {
        wsSession?.run {
            sendSerialized(
                MessageOption(
                    op = "subscribe",
                    args = listOf(
                        OptionArg(channel = "index-candle15m", instId = "BTC-USD")
                    )
                )
            )
            while (true) {
                val message = incoming.receive() as? Frame.Text ?: continue
                onReceive(message.readText())
            }
        }
    }
}
