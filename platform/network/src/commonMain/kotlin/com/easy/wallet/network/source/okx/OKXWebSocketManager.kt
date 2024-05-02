package com.easy.wallet.network.source.okx

import io.ktor.client.HttpClient
import io.ktor.client.plugins.websocket.DefaultClientWebSocketSession
import io.ktor.client.plugins.websocket.wss

class OKXWebSocketManager internal constructor(
    private val httpClient: HttpClient
) {
    private lateinit var wsSession: DefaultClientWebSocketSession

    suspend fun connect(path: String, callback: suspend () -> Unit) {
        httpClient.wss(
            host = "ws.okx.com",
            port = 8443,
            path = path
        ) {
            wsSession = this
            callback()
        }
    }

//    suspend fun subscribe(
//        args: List<OptionArg>,
//        onReceive: (String) -> Unit
//    ) {
//        with(wsSession) {
//            println(wsSession.toString())
//            sendSerialized(
//                MessageOption(
//                    op = "subscribe",
//                    args = args
//                )
//            )
//            try {
//                while (true) {
//                    val message = incoming.receive() as? Frame.Text ?: continue
//                    onReceive(message.readText())
//                }
//            } catch (e: ClosedReceiveChannelException) {
//                e.printStackTrace()
//            }
//        }
//    }
//
//    suspend fun unsubscribe(
//        args: List<OptionArg>
//    ) {
//        wsSession.run {
//            sendSerialized(
//                MessageOption(
//                    op = "unsubscribe",
//                    args = args
//                )
//            )
//        }
//    }
}
