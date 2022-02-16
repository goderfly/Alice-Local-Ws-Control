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
import theme.TelegramColors

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AuthScreen(
    isOpen: MutableState<Boolean>
) {
    val yourEmail = remember { mutableStateOf(TextFieldValue(ContentRepository.yourEmail)) }
    val yourPassword = remember { mutableStateOf(TextFieldValue(ContentRepository.yourPassword)) }
    val botId = remember { mutableStateOf(TextFieldValue(ContentRepository.botId)) }
    val chatId = remember { mutableStateOf(TextFieldValue(ContentRepository.chatId)) }

    val errorStateAuth = remember { mutableStateOf(false) }
    val errorStateEmptyChatId = remember { mutableStateOf(false) }
    val errorStateEmptyBotId = remember { mutableStateOf(false) }

    Column(
        modifier = Modifier.background(TelegramColors.leftBarSelection)
    ) {
        Row {
            OutlinedTextField(
                modifier = Modifier.padding(PaddingValues(8.dp, 8.dp, 8.dp, 8.dp)),
                value = yourEmail.value,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.AlternateEmail,
                        contentDescription = "image",
                        tint = Color.White
                    )
                },
                isError = errorStateAuth.value,
                textStyle = MaterialTheme.typography.body1,
                onValueChange = {
                    errorStateAuth.value = false
                    ContentRepository.yourEmail = it.text
                    yourEmail.value = it
                },
                label = { Text(color = Color.White, text = "Введите адрес почты") }
            )
            OutlinedTextField(

                modifier = Modifier.padding(PaddingValues(8.dp, 8.dp, 8.dp, 8.dp)),
                value = yourPassword.value,
                enabled = true,
                isError = errorStateAuth.value,
                leadingIcon = {
                    Icon(
                        imageVector = Icons.Default.Password,
                        contentDescription = "image",
                        tint = Color.White
                    )
                },
                textStyle = MaterialTheme.typography.body1,
                onValueChange = {
                    errorStateAuth.value = false
                    ContentRepository.yourPassword = it.text
                    yourPassword.value = it
                },
                label = { Text(color = Color.White, text = "Введите пароль") }
            )

        }
        OutlinedTextField(
            modifier = Modifier.padding(PaddingValues(8.dp, 8.dp, 8.dp, 8.dp)).fillMaxWidth(),
            value = botId.value,
            enabled = true,
            isError = errorStateEmptyBotId.value,
            textStyle = MaterialTheme.typography.body1,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "image",
                    tint = Color.White
                )
            },
            onValueChange = {
                ContentRepository.botId = it.text
                errorStateEmptyBotId.value = false
                botId.value = it
            },
            label = { Text(color = Color.White, text = "Bot ID для отправки") }
        )
        OutlinedTextField(

            modifier = Modifier.padding(PaddingValues(8.dp, 8.dp, 8.dp, 8.dp)).fillMaxWidth(),
            value = chatId.value,
            enabled = true,
            isError = errorStateEmptyChatId.value,
            textStyle = MaterialTheme.typography.body1,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "image",
                    tint = Color.White
                )
            },
            onValueChange = {
                ContentRepository.chatId = it.text
                errorStateEmptyChatId.value = false
                chatId.value = it
            },
            label = { Text(color = Color.White, text = "Chat ID куда отправлять") }
        )
        OutlinedButton(
            modifier = Modifier.fillMaxWidth().padding(PaddingValues(16.dp, 8.dp, 16.dp, 16.dp)),
            onClick = {
                if (ContentRepository.chatId.isNotBlank()) {
                    tryToConnect(errorStateAuth, isOpen)
                } else {
                    errorStateEmptyChatId.value = true
                }
            }
        ) {
            Text("Авторизоваться")
        }
    }

}

fun tryToConnect(errorState: MutableState<Boolean>, isOpen: MutableState<Boolean>) {
    ReceiveMailInteractor.connectToImapMail(

        onConnectResultStatus = { onConnectResultStatus ->
            when (onConnectResultStatus) {
                true -> isOpen.value = false
                else -> errorState.value = true
            }
        },

        onInteredInFolder = { imapFolder ->

        },

        onMessageReceived = { meassage ->

        }

    )
}
