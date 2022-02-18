import alicews.BotHandler
import alicews.YandexStationComminication
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import theme.TelegramColors

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ConfigureScreen(
    isOpen: MutableState<Boolean>
) {
    val telegramBotToken =
        remember { mutableStateOf(TextFieldValue(ContentRepository.telegramBotToken)) }
    val telegramGroupId =
        remember { mutableStateOf(TextFieldValue(ContentRepository.telegramGroupId)) }
    val telegramIdPinMessage =
        remember { mutableStateOf(TextFieldValue(ContentRepository.telegramIdPinMessage)) }
    val localAliceIp = remember { mutableStateOf(TextFieldValue(ContentRepository.localAliceIp)) }
    val localAlicePort =
        remember { mutableStateOf(TextFieldValue(ContentRepository.localAlicePort)) }

    val errorTokenState = remember { mutableStateOf(false) }
    val errorGroupIdState = remember { mutableStateOf(false) }
    val errorPinnedMessageState = remember { mutableStateOf(false) }
    val errorAliceIpState = remember { mutableStateOf(false) }
    val errorAlicePortState = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.background(TelegramColors.leftBarSelection)
    ) {
        OutlinedTextField(
            modifier = Modifier.padding(PaddingValues(8.dp, 8.dp, 8.dp, 8.dp)).fillMaxWidth(),
            value = telegramBotToken.value,
            enabled = true,
            isError = errorTokenState.value,
            textStyle = MaterialTheme.typography.body1,
            onValueChange = {
                ContentRepository.telegramBotToken = it.text
                errorTokenState.value = false
                telegramBotToken.value = it
            },
            label = { Text(color = Color.White, text = "Telegram Bot Token") }
        )
        OutlinedTextField(

            modifier = Modifier.padding(PaddingValues(8.dp, 8.dp, 8.dp, 8.dp)).fillMaxWidth(),
            value = telegramGroupId.value,
            enabled = true,
            isError = errorGroupIdState.value,
            textStyle = MaterialTheme.typography.body1,
            onValueChange = {
                ContentRepository.telegramGroupId = it.text
                errorGroupIdState.value = false
                telegramGroupId.value = it
            },
            label = { Text(color = Color.White, text = "Group ID") }
        )
        OutlinedTextField(

            modifier = Modifier.padding(PaddingValues(8.dp, 8.dp, 8.dp, 8.dp)).fillMaxWidth(),
            value = telegramIdPinMessage.value,
            enabled = true,
            isError = errorPinnedMessageState.value,
            textStyle = MaterialTheme.typography.body1,
            onValueChange = {
                ContentRepository.telegramIdPinMessage = it.text
                errorPinnedMessageState.value = false
                telegramIdPinMessage.value = it
            },
            label = { Text(color = Color.White, text = "Pinned Message ID") }
        )
        Row {
            OutlinedTextField(
                modifier = Modifier.padding(PaddingValues(8.dp, 8.dp, 8.dp, 8.dp)),
                value = localAliceIp.value,

                isError = errorAliceIpState.value,
                textStyle = MaterialTheme.typography.body1,
                onValueChange = {
                    errorAliceIpState.value = false
                    ContentRepository.localAliceIp = it.text
                    localAliceIp.value = it
                },
                label = { Text(color = Color.White, text = "IP адрес алисы") }
            )
            OutlinedTextField(

                modifier = Modifier.padding(PaddingValues(8.dp, 8.dp, 8.dp, 8.dp)),
                value = localAlicePort.value,
                enabled = true,
                isError = errorAlicePortState.value,
                textStyle = MaterialTheme.typography.body1,
                onValueChange = {
                    errorAlicePortState.value = false
                    ContentRepository.localAlicePort = it.text
                    localAlicePort.value = it
                },
                label = { Text(color = Color.White, text = "Порт алисы") }
            )

        }
        OutlinedButton(
            modifier = Modifier.fillMaxWidth().padding(PaddingValues(16.dp, 8.dp, 16.dp, 16.dp)),
            onClick = {

                when {
                    telegramBotToken.value.text.isBlank() -> {
                        errorTokenState.value = true
                        return@OutlinedButton
                    }
                    telegramGroupId.value.text.isBlank() -> {
                        errorGroupIdState.value = true
                        return@OutlinedButton
                    }
                    telegramIdPinMessage.value.text.isBlank() -> {
                        errorPinnedMessageState.value = true
                        return@OutlinedButton
                    }
                    localAliceIp.value.text.isBlank() -> {
                        errorAliceIpState.value = true
                        return@OutlinedButton
                    }
                    localAlicePort.value.text.isBlank() -> {
                        errorAlicePortState.value = true
                        return@OutlinedButton
                    }
                }

                runCatching { BotHandler.registerTelegramBot() }
                    .onFailure {
                        errorTokenState.value = true
                    }
                    .onSuccess {
                        initStartInfo()
                        YandexStationComminication.sendCommandToAlice("Включи случайную песню")
                        isOpen.value = false
                    }
            }
        ) {
            Text("Запустить")
        }
    }


}
