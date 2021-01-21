package com.mirbor

import com.google.gson.Gson
import com.mirbor.models.AliceResponse
import com.mirbor.models.Response
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okhttp3.tls.HandshakeCertificates
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier

object YandexStationComminication {
    private const val LOCAL_ADDRESS = "rbtk.cloudns.cc"
    private const val LOCAL_PORT = "4444"

    private lateinit var ws: WebSocket
    private lateinit var certificate: X509Certificate
    private lateinit var jwtDeviceToken: String
    private lateinit var deviceId: String

    private val listener = WebSocketCallbacks()

    private fun init() {
        val certificates = HandshakeCertificates.Builder()
                .addTrustedCertificate(certificate)
                .build()

        val client = OkHttpClient.Builder()
                .sslSocketFactory(certificates.sslSocketFactory(), certificates.trustManager)
                .hostnameVerifier(HostnameVerifier { _, _ -> true })
                .build()

        val request = Request.Builder()
                .addHeader("Origin", "https://yandex.ru/")
                .url("wss://${LOCAL_ADDRESS}:${LOCAL_PORT}/")
                .build()

        ws = client.newWebSocket(request, listener)
    }

    fun setCertAndInit(cert: X509Certificate) {
        certificate = cert
        init()
    }

    fun setJwtDeviceToken(token: String) {
        jwtDeviceToken = token
    }

    fun setDeviceId(devId: String) {
        deviceId = devId
    }

    fun sendCommandToAlice(text: String) {
            val testPayload = """
            {
                "conversationToken": "$jwtDeviceToken",
                "id": "$deviceId",
                "sentTime": "${System.currentTimeMillis() * 1000}",
                "payload": {
                    "command": "sendText",
                    "text": "$text"
                }
            }""".trimIndent()


            ws.send(testPayload)
    }


    class WebSocketCallbacks : WebSocketListener() {
        private var savedSongFullname: String = "WTF?"

        override fun onMessage(webSocket: WebSocket, text: String) {

            val responseObject = Gson().fromJson(text, AliceResponse::class.java)
            val songTitle = responseObject.state.playerState.title
            val songSubTitle = responseObject.state.playerState.subtitle
            val songFullName = "$songTitle - $songSubTitle"

            updatePinnedMessageIfNeed(songFullName)
        }

        private fun updatePinnedMessageIfNeed(songFullName: String) {
            if (songFullName != savedSongFullname) {
                try {
                    Main.bot.editPinnedMessage(songFullName)
                } catch (e: Exception) {
                    if (e.cause.toString().contains("message content and reply markup are exactly the same")) {
                        return
                    }
                    println(e.message)
                }
                savedSongFullname = songFullName
            }
        }

        override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
            println("Opened")
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            println("Closed $code $reason")
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: okhttp3.Response?) {
            println(t)
        }
    }

}