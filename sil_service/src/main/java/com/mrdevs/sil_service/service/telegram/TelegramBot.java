package com.mrdevs.sil_service.service.telegram;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {

    private final Environment env;
    private final TelegramService telegramService;

    private final String welcomeMessage = "Welcome to Simulation IoT of Locomotive Bots.\n\n"
            + //
            "/subscribe - for subscribe every 10 seconds\n"
            + //
            "/subscribe_1m - for subscribe every 1 minute\n"
            + //
            "/subscribe_15m - for subscribe every 15 minutes\n\n"
            + //
            "/help - to get this message again\n";

    @Override
    public String getBotUsername() {
        return env.getProperty("telegrambots.botUsername");
    }

    @Override
    public String getBotToken() {
        return env.getProperty("telegrambots.botToken");
    }

    @Override
    public void onUpdateReceived(Update update) {

        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();
            String message = "";

            switch (messageText.toLowerCase()) {
                case "/start", "/help":
                    message = welcomeMessage;
                    break;
                case "/subscribe":
                    message = telegramService.subscribeLocomotiveSummary(chatId, "10s");
                    break;
                case "/subscribe_1m":
                    message = telegramService.subscribeLocomotiveSummary(chatId, "1m");
                    break;
                case "/subscribe_15m":
                    message = telegramService.subscribeLocomotiveSummary(chatId, "15m");
                    break;
                case "/locomotive", "locomotive", "status", "locomotive status":
                    message = telegramService.createLatestLocomotiveSummaryMessage();
                    break;

                default:
                    message = "Command " + messageText + " is not found.";

            }

            sendTextMessage(chatId, message);
        }
    }

    private void sendTextMessage(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

}
