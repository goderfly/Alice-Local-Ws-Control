import com.notkamui.kourrier.core.Kourrier
import com.notkamui.kourrier.core.KourrierAuthenticationException
import com.notkamui.kourrier.imap.*
import java.util.*

object ReceiveMailInteractor {

    fun connectToImapMail(
        onConnectResultStatus: (Boolean) -> Unit = {},
        onInteredInFolder: (KourrierFolder) -> Unit,
        onMessageReceived: (KourrierIMAPMessage) -> Unit
    ) {
        try {
            val session = Kourrier.imap(
                hostname = "imap.yandex.ru",
                port = 993,
                username = ContentRepository.yourEmail,
                password = ContentRepository.yourPassword
            )

            session {
                folder(
                    name = "INBOX",
                    mode = KourrierFolderMode.ReadWrite,
                    keepAlive = true,
                    listener = object: KourrierFolderListener{
                        override fun onMessageEnvelopeChanged(message: KourrierIMAPMessage) {

                        }

                        override fun onMessageFlagsChanged(message: KourrierIMAPMessage) {

                        }

                        override fun onMessageReceived(message: KourrierIMAPMessage) {
                            onMessageReceived.invoke(message)
                        }

                        override fun onMessageRemoved(message: KourrierIMAPMessage) {

                        }

                    }
                ) {
                    onConnectResultStatus.invoke(true)
                    onInteredInFolder.invoke(this)
                }
            }

        } catch (e: KourrierAuthenticationException) {
            onConnectResultStatus.invoke(false)
            e.printStackTrace()
        }


    }

    fun getMessageHeader(message: KourrierIMAPMessage) = message.from[0].toString()
        .substringAfter("<")
        .substringBefore(">")
        .replace("< >", "")
}