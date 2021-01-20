package com.mirbor

import com.google.gson.Gson
import com.mirbor.models.Device
import com.mirbor.models.JwtDeviceResponse
import com.mirbor.models.Response
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

object Main {
    private val scheduler = Executors.newScheduledThreadPool(1)
    private lateinit var yandexDevice: Device

    @JvmStatic
    fun main(args: Array<String>) {

        val (id, platform) = ApiManager().getDeviceList().run {
            val responseString = this.third.component1()
            val responseObject = Gson().fromJson(responseString, Response::class.java)
            yandexDevice = responseObject.devices.first()

            return@run yandexDevice.id to yandexDevice.platform
        }

        val jwtToken = ApiManager().getJwtToken(id, platform).run {
            val responseString = this.third.component1()
            val responseObject = Gson().fromJson(responseString, JwtDeviceResponse::class.java)

            return@run responseObject.token
        }


    }

    private fun startPingForAwake() {
        scheduler.scheduleAtFixedRate({}, 15, 15, TimeUnit.MINUTES)
    }
}