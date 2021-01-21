package com.mirbor

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton


class AliceControlTelegramBot : TelegramLongPollingBot() {


    override fun getBotUsername() = BOT_NAME

    override fun getBotToken() = BOT_TOKEN

    override fun onUpdateReceived(update: Update) {
        println(update.toString())
        try {
            if (update.hasCallbackQuery()) {
                val callBackData = update.callbackQuery.data
                val callBackMessageId = update.callbackQuery.message.messageId.toInt()
                with(callBackData) {

                }
                return
            }

            if (update.message.chat.id != BOT_WORK_GROUP) {
                return
            }

            println(update.toString())
        } catch (e: Exception) {
            println("onUpdateReceived exception ${e.message}")
            e.printStackTrace()
        }
    }



    companion object {
        private const val BOT_TOKEN = "1586716876:AAEjMNEFRTkmdbjt5Sol9CopX8QeshfgPzY"
        private const val BOT_NAME = "yandex_alice_bot"
        private const val BOT_WORK_GROUP = -1001489932708
    }

    fun sendNewNotification(text: String?) {
        execute(SendMessage(BOT_WORK_GROUP, "$text"))
    }


    fun editPinnedMessage(messageId: Int, text: String) {
        execute(
            EditMessageText()
                .setText("Сейчас играет: $text")
                .setChatId(BOT_WORK_GROUP)
                .setMessageId(messageId)
        )
    }

}
