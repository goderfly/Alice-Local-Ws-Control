package com.mirbor

import com.google.gson.Gson
import com.mirbor.models.Device
import com.mirbor.models.JwtDeviceResponse
import com.mirbor.models.Response
import okhttp3.tls.decodeCertificatePem
import org.telegram.telegrambots.ApiContextInitializer
import org.telegram.telegrambots.meta.TelegramBotsApi

object Main {
    lateinit var bot: AliceControlTelegramBot

    @JvmStatic
    fun registerTelegramBot() {
        println("Register telegram bot")
        ApiContextInitializer.init()
        bot = AliceControlTelegramBot().apply {
            TelegramBotsApi().registerBot(this)
        }
    }

    @JvmStatic
    fun main(args: Array<String>) {
        registerTelegramBot()
        initStartInfo()



    }

    private fun initStartInfo() {
        lateinit var yandexDevice: Device

        val (id, platform) = ApiManager().getDeviceList().run {
            val responseString = this.third.component1()
            val responseObject = Gson().fromJson(responseString, Response::class.java)
            yandexDevice = responseObject.devices.first()
            val cert = yandexDevice.glagol.security.server_certificate.decodeCertificatePem()
            val deviceId = yandexDevice.id

            YandexStationComminication.setCert(cert)
            YandexStationComminication.setDeviceId(deviceId)

            return@run deviceId to yandexDevice.platform
        }

        val jwtToken = ApiManager().getJwtToken(id, platform).run {
            val responseString = this.third.component1()
            val responseObject = Gson().fromJson(responseString, JwtDeviceResponse::class.java)
            val token = responseObject.token

            YandexStationComminication.setJwtDeviceToken(token)

            return@run token
        }
    }
}