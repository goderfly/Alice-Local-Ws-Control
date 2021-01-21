package com.mirbor

import org.telegram.telegrambots.bots.TelegramLongPollingBot
import org.telegram.telegrambots.meta.api.methods.AnswerInlineQuery
import org.telegram.telegrambots.meta.api.methods.send.SendMessage
import org.telegram.telegrambots.meta.api.methods.updatingmessages.EditMessageText
import org.telegram.telegrambots.meta.api.objects.Update
import org.telegram.telegrambots.meta.api.objects.inlinequery.inputmessagecontent.InputTextMessageContent
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResult
import org.telegram.telegrambots.meta.api.objects.inlinequery.result.InlineQueryResultArticle
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton


class AliceControlTelegramBot : TelegramLongPollingBot() {


    override fun getBotUsername() = BOT_NAME

    override fun getBotToken() = BOT_TOKEN

    override fun onUpdateReceived(update: Update) {

        println(update.toString())
        try {

            if (update.hasMessage() && update.message.hasViaBot()){
                YandexStationComminication.sendCommandToAlice(update.message.text)
            }

            if (update.hasInlineQuery()) {
                val r1: InlineQueryResult = InlineQueryResultArticle(
                        update.inlineQuery.id,
                        "Нажмите, что бы отправить команду",
                        InputTextMessageContent("${update.inlineQuery.query}"),
                        null,
                        null,
                        false,
                        "${update.inlineQuery.query}",
                        null,
                        null,
                        null
                        )

                Main.bot.execute(
                        AnswerInlineQuery().apply {
                            inlineQueryId = update.inlineQuery.id
                            results = listOf(r1)
                            cacheTime = 9999
                        }
                )

            }

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

                        InlineKeyboardButton("⏮").apply {
                            callbackData = "previous_"
                        },

                        InlineKeyboardButton("⏸").apply {
                            callbackData = "pause_"
                        },
                        InlineKeyboardButton("⏭").apply {
                            callbackData = "next_"
                        }
                ), mutableListOf(
                        InlineKeyboardButton("Громкость -").apply {
                            callbackData = "volume-_"
                        },
                        InlineKeyboardButton("Громкость +").apply {
                            callbackData = "volume+_"
                        }

                ), mutableListOf(
                        InlineKeyboardButton("Случайная песня КиШ").apply {
                            callbackData = "random_"
                        }

                )
                )
        )

        execute(
                EditMessageText().apply {
                    chatId = BOT_WORK_GROUP
                    setText("Сейчас играет: $text")
                    messageId = PINNED_MESSAGE_ID
                    replyMarkup = inlineKeyboardMarkup
                }
        )


    }

}
