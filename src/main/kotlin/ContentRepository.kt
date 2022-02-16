
import utils.StorableLong
import utils.StorableString

object ContentRepository {
    var telegramGroupId by StorableString("")
    var telegramBotToken by StorableString("")
    var telegramIdPinMessage by StorableString("")
    var localAliceIp by StorableString("")
    var localAlicePort by StorableString("")
}