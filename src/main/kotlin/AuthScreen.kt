import alicews.ApiManager
import alicews.YandexStationComminication
import alicews.models.Device
import alicews.models.JwtDeviceResponse
import alicews.models.Response
import alicews.toJson
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AlternateEmail
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Password
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.google.gson.Gson
import okhttp3.tls.decodeCertificatePem
import theme.TelegramColors

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ConfigureScreen(
    isOpen: MutableState<Boolean>
) {
    val telegramBotToken = remember { mutableStateOf(TextFieldValue(ContentRepository.telegramBotToken)) }
    val telegramGroupId = remember { mutableStateOf(TextFieldValue(ContentRepository.telegramGroupId)) }
    val telegramIdPinMessage = remember { mutableStateOf(TextFieldValue(ContentRepository.telegramIdPinMessage)) }
    val localAliceIp = remember { mutableStateOf(TextFieldValue(ContentRepository.localAliceIp)) }
    val localAlicePort = remember { mutableStateOf(TextFieldValue(ContentRepository.localAlicePort)) }

    val errorStateAuth = remember { mutableStateOf(false) }
    val errorStateEmptyChatId = remember { mutableStateOf(false) }
    val errorStateEmptyBotId = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.background(TelegramColors.leftBarSelection)
    ) {
        OutlinedTextField(
            modifier = Modifier.padding(PaddingValues(8.dp, 8.dp, 8.dp, 8.dp)).fillMaxWidth(),
            value = localAliceIp.value,
            enabled = true,
            isError = errorStateEmptyBotId.value,
            textStyle = MaterialTheme.typography.body1,
            onValueChange = {
                ContentRepository.localAliceIp = it.text
                errorStateEmptyBotId.value = false
                localAliceIp.value = it
            },
            label = { Text(color = Color.White, text = "Telegram Bot Token") }
        )
        OutlinedTextField(

            modifier = Modifier.padding(PaddingValues(8.dp, 8.dp, 8.dp, 8.dp)).fillMaxWidth(),
            value = localAlicePort.value,
            enabled = true,
            isError = errorStateEmptyChatId.value,
            textStyle = MaterialTheme.typography.body1,
            onValueChange = {
                ContentRepository.localAlicePort = it.text
                errorStateEmptyChatId.value = false
                localAlicePort.value = it
            },
            label = { Text(color = Color.White, text = "Group ID") }
        )
        OutlinedTextField(

            modifier = Modifier.padding(PaddingValues(8.dp, 8.dp, 8.dp, 8.dp)).fillMaxWidth(),
            value = localAlicePort.value,
            enabled = true,
            isError = errorStateEmptyChatId.value,
            textStyle = MaterialTheme.typography.body1,
            onValueChange = {
                ContentRepository.localAlicePort = it.text
                errorStateEmptyChatId.value = false
                localAlicePort.value = it
            },
            label = { Text(color = Color.White, text = "Pinned Message ID") }
        )
        Row {
            OutlinedTextField(
                modifier = Modifier.padding(PaddingValues(8.dp, 8.dp, 8.dp, 8.dp)),
                value = telegramBotToken.value,

                isError = errorStateAuth.value,
                textStyle = MaterialTheme.typography.body1,
                onValueChange = {
                    errorStateAuth.value = false
                    ContentRepository.telegramGroupId = it.text
                    telegramGroupId.value = it
                },
                label = { Text(color = Color.White, text = "IP адрес алисы") }
            )
            OutlinedTextField(

                modifier = Modifier.padding(PaddingValues(8.dp, 8.dp, 8.dp, 8.dp)),
                value = telegramIdPinMessage.value,
                enabled = true,
                isError = errorStateAuth.value,
                textStyle = MaterialTheme.typography.body1,
                onValueChange = {
                    errorStateAuth.value = false
                    ContentRepository.telegramIdPinMessage = it.text
                    telegramIdPinMessage.value = it
                },
                label = { Text(color = Color.White, text = "Порт алисы") }
            )

        }
        OutlinedButton(
            modifier = Modifier.fillMaxWidth().padding(PaddingValues(16.dp, 8.dp, 16.dp, 16.dp)),
            onClick = {
                if (ContentRepository.localAlicePort.isNotBlank()) {
                    registerTelegramBot()
                    initStartInfo()
                    YandexStationComminication.sendCommandToAlice("Включи король и шут два монаха в одну ночь")
                } else {
                    errorStateEmptyChatId.value = true
                }
            }
        ) {
            Text("Запустить")
        }
    }


}
