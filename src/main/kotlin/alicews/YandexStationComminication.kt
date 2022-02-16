package alicews

import com.google.gson.Gson
import alicews.models.AliceResponse
import bot
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okhttp3.tls.HandshakeCertificates
import org.telegram.telegrambots.meta.exceptions.TelegramApiRequestException
import java.security.cert.X509Certificate
import javax.net.ssl.HostnameVerifier

object YandexStationComminication {
    private const val LOCAL_ADDRESS = "192.168.88.22"//""rbtk.cloudns.cc"
    private const val LOCAL_PORT = "1961"

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

            println("sendCommandToAlice $testPayload")
            ws.send(testPayload)
    }


    class WebSocketCallbacks : WebSocketListener() {
        private var savedSongFullname: String = "Расскажи анекдот"

        override fun onMessage(webSocket: WebSocket, text: String) {
            println("onMessageWS $text")
            val responseObject = Gson().fromJson(text, AliceResponse::class.java)
            val songTitle = responseObject.state.playerState.title
            val songSubTitle = responseObject.state.playerState.subtitle
            val songFullName = "$songTitle - $songSubTitle"

            updatePinnedMessageIfNeed(songFullName)
        }

        private fun updatePinnedMessageIfNeed(songFullName: String) {
            if (songFullName != savedSongFullname) {
                try {
                    bot.editPinnedMessage(songFullName)
                } catch (e: Exception) {
                    if ((e as? TelegramApiRequestException)?.apiResponse?.contains("message content and reply markup are exactly the same") == true) {
                        return
                    }
                    println("updatePinnedMessageIfNeed " + e.message)
                }
                savedSongFullname = songFullName
            }
        }

        override fun onOpen(webSocket: WebSocket, response: okhttp3.Response) {
            println("Opened")
        }

        override fun onClosed(webSocket: WebSocket, code: Int, reason: String) {
            println("Closed $code $reason")
            bot.sendNewNotification("Чет мне поплахело, сокет упал $code $reason")
        }

        override fun onFailure(webSocket: WebSocket, t: Throwable, response: okhttp3.Response?) {
            println("onFailure $t")
            bot.sendNewNotification("Чет мне поплахело ${t.message}")
        }
    }

}