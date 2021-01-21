package com.mirbor

import com.google.gson.Gson
import com.mirbor.models.Device
import com.mirbor.models.JwtDeviceResponse
import com.mirbor.models.Response
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okhttp3.tls.HandshakeCertificates
import okhttp3.tls.decodeCertificatePem
import java.security.cert.X509Certificate
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import javax.net.ssl.HostnameVerifier
import javax.net.ssl.SSLSession

object Main {
    private val LOCAL_ADDRESS = "192.168.88.22"
    private val LOCAL_PORT = "1961"
    private val scheduler = Executors.newScheduledThreadPool(1)
    private lateinit var yandexDevice: Device
    private lateinit var ws: WebSocket
    private lateinit var cert: X509Certificate

    private val listener = object : WebSocketListener() {
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

    @JvmStatic
    fun main(args: Array<String>) {
        val (id, platform) = ApiManager().getDeviceList().run {
            val responseString = this.third.component1()
            val responseObject = Gson().fromJson(responseString, Response::class.java)
            yandexDevice = responseObject.devices.first()
            println("Private Key")
            println(yandexDevice.glagol.security.server_private_key)
            println("Server sert")
            println(yandexDevice.glagol.security.server_certificate)
            cert = yandexDevice.glagol.security.server_certificate.decodeCertificatePem()
            return@run yandexDevice.id to yandexDevice.platform
        }

        val jwtToken = ApiManager().getJwtToken(id, platform).run {
            val responseString = this.third.component1()
            val responseObject = Gson().fromJson(responseString, JwtDeviceResponse::class.java)

            return@run responseObject.token
        }

        val certificates = HandshakeCertificates.Builder()
                .addTrustedCertificate(cert)
                .build()

        val client = OkHttpClient.Builder()
                .sslSocketFactory(certificates.sslSocketFactory(), certificates.trustManager)
                .hostnameVerifier(HostnameVerifier { hostname, session -> true })
                .build()

        val request = Request.Builder()
                .addHeader("Origin", "http://yandex.ru/")
                .url("wss://$LOCAL_ADDRESS:$LOCAL_PORT/")
                .build()

        ws = client.newWebSocket(request, listener)
    }

    private fun startPingForAwake() {
        scheduler.scheduleAtFixedRate({}, 15, 15, TimeUnit.MINUTES)
    }
}