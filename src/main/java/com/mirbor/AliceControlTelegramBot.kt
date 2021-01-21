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
                    when {
                        startsWith("next_") -> {
                            println("⏭")
                            YandexStationComminication.sendCommandToAlice("Следующая песня")
                        }
                        startsWith("pause_") -> {
                            println("⏸")
                            YandexStationComminication.sendCommandToAlice("Поставь на паузу")
                        }
                        startsWith("previous_") -> {
                            println("⏮")
                            YandexStationComminication.sendCommandToAlice("Предыдущая песня")
                        }
                        startsWith("volume-_") -> {
                            println("Валумэ -")
                            YandexStationComminication.sendCommandToAlice("Уменьши громкость")
                        }
                        startsWith("volume+_") -> {
                            println("Валумэ +")
                            YandexStationComminication.sendCommandToAlice("Уменьшить громкость")
                        }
                        startsWith("random_") -> {
                            println("Случайная песня КиШ")
                            YandexStationComminication.sendCommandToAlice("Включи король и шут в перемешку")
                        }
                        else -> println("unknown callback prefix $callBackData")
                    }
                }
                return
            }

            if (update.message?.chat?.id.toString() != BOT_WORK_GROUP) {
                return
            }

            println(update.toString())
        } catch (e: Exception) {
            println("onUpdateReceived exception ${e.message}")
            e.printStackTrace()
        }
    }


    companion object {
        private const val BOT_TOKEN = "1503011046:AAG-ZsxsXNcuNEz9NOsMES2vvE7sXyOEsm4"
        private const val BOT_NAME = "yandex_alice_bot"
        private const val BOT_WORK_GROUP = "-1001489932708"
        private const val PINNED_MESSAGE_ID = 1066
    }

    fun sendNewNotification(text: String?) {
        execute(SendMessage(BOT_WORK_GROUP, "$text"))
    }

    fun editPinnedMessage(text: String) {
        val inlineKeyboardMarkup = InlineKeyboardMarkup(
                listOf(mutableListOf(

                        InlineKeyboardButton("⏮")
                                .setCallbackData("previous_"),
                        InlineKeyboardButton("⏸")
                                .setCallbackData("pause_"),
                        InlineKeyboardButton("⏭")
                                .setCallbackData("next_")

                ), mutableListOf(
                        InlineKeyboardButton("Громкость -")
                                .setCallbackData("volume-_"),
                        InlineKeyboardButton("Громкость +")
                                .setCallbackData("volume+_")
                ), mutableListOf(
                        InlineKeyboardButton("Случайная песня КиШ")
                                .setCallbackData("random_")
                )
                )
        )

        execute(
                EditMessageText()
                        .setText("Сейчас играет: $text")
                        .setChatId(BOT_WORK_GROUP)
                        .setMessageId(PINNED_MESSAGE_ID)
                        .setReplyMarkup(inlineKeyboardMarkup)
        )


    }

}
