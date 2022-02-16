@file:Suppress("NAME_SHADOWING")

import ContentRepository.chatId
import com.pengrad.telegrambot.TelegramBot
import com.pengrad.telegrambot.model.request.InlineKeyboardButton
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup
import com.pengrad.telegrambot.model.request.ParseMode
import com.pengrad.telegrambot.request.SendMessage

object TelegramBot {
    private val bot = TelegramBot(ContentRepository.botId)

    fun sendMailToTelegram(from: String, header: String, body: String, date: String) {
        fun getOpenMailBtn(): InlineKeyboardMarkup {
            return InlineKeyboardMarkup(
                InlineKeyboardButton("Открыть почту").url("https://mail.yandex.ru/")
            )
        }
        val from = "<b>От:</b> $from\n"
        val header = "<b>Тема:</b> $header\n"
        val date = "<b>Когда:</b> $date\n"
        val body = "<b>Содержание:</b> $body\n"


        try {
            println("try to send")
            bot.execute(
                SendMessage(chatId, "$date$from$header")
                    .parseMode(ParseMode.HTML)
                    .replyMarkup(getOpenMailBtn())
            )
        } catch (e: Exception) {
            println(e.message)
            e.printStackTrace()
        }
    }



}