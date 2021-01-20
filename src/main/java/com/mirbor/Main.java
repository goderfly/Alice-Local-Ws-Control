package com.mirbor;

import com.mirbor.cms.CMSInteractor;
import com.mirbor.snapshot.SnapshotInteractor;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.ParseMode;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.mirbor.JiraTelegramBot.TELEGRAM_CHANNEL_ID;
import static spark.Spark.port;
import static spark.Spark.post;

public class Main {
    private static final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
    static JiraTelegramBot bot;

    public static void main(String[] args) {

    }

    private static void startPingForAwake() {
        scheduler.scheduleAtFixedRate(() -> {
            new ApiManager().getPingApp();
        }, 15, 15, TimeUnit.MINUTES);
    }

}
