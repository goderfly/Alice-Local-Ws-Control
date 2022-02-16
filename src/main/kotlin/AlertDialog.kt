import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CopyAll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import theme.TelegramColors
import theme.typography
import java.awt.Toolkit
import java.awt.datatransfer.Clipboard
import java.awt.datatransfer.StringSelection

data class DialogEntity(
    val isShowed: MutableState<Boolean> = mutableStateOf(false),
    var title: MutableState<String> = mutableStateOf("Информация"),
    var message: MutableState<String> = mutableStateOf("Пусто")

)

@ExperimentalMaterialApi
@Preview
@Composable
fun showAlertDialog(
    showDialog: MutableState<DialogEntity>
) {
    val trayState = rememberTrayState()
    val notification = rememberNotification(showDialog.value.title.value, "Скопировано в буфер обмена")

    if (showDialog.value.isShowed.value) {
        Dialog(
            state = rememberDialogState(
                size = WindowSize(900.dp, 190.dp)
            ),
            title = showDialog.value.title.value,
            onCloseRequest = {
                showDialog.value.isShowed.value = false
            },
            content = {
                Row(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(TelegramColors.cardBackground),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = showDialog.value.message.value,
                        style = typography.h5,
                        modifier = Modifier
                            .fillMaxWidth(0.92f),
                        color = Color.White
                    )
                    IconButton(
                        modifier = Modifier
                            .size(64.dp)
                            .alpha(ContentAlpha.medium),
                        onClick = {
                            val selection = StringSelection(showDialog.value.message.value)
                            val clipboard: Clipboard = Toolkit.getDefaultToolkit().systemClipboard
                            clipboard.setContents(selection, selection)
                            showDialog.value.isShowed.value = false
                            trayState.sendNotification(notification)
                        }) {
                        Icon(imageVector = Icons.Default.CopyAll, contentDescription = null, tint = Color.White)
                    }
                }

            }
        )
    }
}


