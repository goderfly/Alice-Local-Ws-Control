import alicews.ApiManager
import alicews.BotHandler
import alicews.YandexStationComminication
import alicews.models.Device
import alicews.models.JwtDeviceResponse
import alicews.models.Response
import alicews.toJson
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import com.google.gson.Gson
import okhttp3.tls.decodeCertificatePem
import theme.Colors
import theme.typography


@OptIn(ExperimentalComposeUiApi::class)
fun main() = application {
    println("main")
    val isOpen = remember { mutableStateOf(true) }

    val trayState = rememberTrayState()
    Tray(
        state = trayState,
        icon = TrayIcon,
        hint = "Локальное управление Алисой",
        menu = {
            Item(
                text = "Тест",
                onClick = {
                    BotHandler.sendNewNotification("test")
                }
            )
            Item(
                text = "Сменить конфигурацию",
                onClick = {
                    isOpen.value = true
                }
            )
            Item(
                text = "Выход",
                onClick = {
                    this@application.exitApplication()
                }
            )
        }
    )

    if (isOpen.value) {
        mainWindow(isOpen)
    }
}

fun initStartInfo() {
    println("initStartInfo")
    lateinit var yandexDevice: Device

    val (id, platform) = ApiManager().getDeviceList().run {
        val responseString = this.third.component1()
        val responseObject = Gson().fromJson(responseString, Response::class.java)
        yandexDevice = responseObject.devices.first()
        println("yandexDevice ${yandexDevice.toJson()}")
        val cert = yandexDevice.glagol.security.server_certificate.decodeCertificatePem()
        val deviceId = yandexDevice.id

        YandexStationComminication.setCertAndInit(cert)
        YandexStationComminication.setDeviceId(deviceId)

        return@run deviceId to yandexDevice.platform
    }

    val jwtToken = ApiManager().getJwtToken(id, platform).run {
        val responseString = this.third.component1()
        val responseObject = Gson().fromJson(responseString, JwtDeviceResponse::class.java)
        val token = responseObject.token
        println("jwtToken ${token}")
        YandexStationComminication.setJwtDeviceToken(token)

        return@run token
    }
}





@Composable
private fun mainWindow(isOpen: MutableState<Boolean>) {
    Window(
        title = "Локальное управление Алисой",
        onCloseRequest = {
            println("onCloseRequest")
            isOpen.value = false
        },
        state = WindowState(size = WindowSize(606.dp, 420.dp), position = WindowPosition(Alignment.Center)),
        resizable = false,
        alwaysOnTop = false
    ) {
        MaterialTheme(
            colors = Colors.material,
            typography = typography,
        ) {
            ConfigureScreen(isOpen)
        }

    }
}

object TrayIcon : Painter() {
    override val intrinsicSize = Size(256f, 256f)

    override fun DrawScope.onDraw() {
        drawOval(Color(0xFFFFA500))
    }
}


