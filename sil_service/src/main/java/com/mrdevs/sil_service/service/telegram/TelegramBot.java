package com.mrdevs.sil_service.service.telegram;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import com.mrdevs.sil_service.model.postgresql.Subscriber;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    private static final Logger logger = LoggerFactory.getLogger(TelegramBot.class);

    private final Environment env;
    private final TelegramService telegramService;

    private final String welcomeMessage = "Welcome to Simulation IoT of Locomotive Bots.\n\n"

            + //
            "/locomotive - get latest locomotive status\n\n"
            + //
            "Subscribe\n"
            + //
            "/subscribe - subscribe every 10 seconds\n"
            + //
            "/subscribe_1m - subscribe every 1 minute\n"
            + //
            "/subscribe_15m - subscribe every 15 minutes\n"
            + //
            "/unsubscribe - unsubscribe\n\n"
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
                case "/unsubscribe":
                    message = telegramService.unsubscribeLocomotiveSummary(chatId);
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

    // handle subscriber interval 10s
    @Scheduled(fixedDelay = 10000)
    public void subscriber1s() {
        logger.info("scheduler 10s");
        List<Subscriber> subscribers = telegramService.getSubscribers("10s");

        for (Subscriber subscriber : subscribers) {
            sendTextMessage(subscriber.getChatId(), telegramService.createLatestLocomotiveSummaryMessage());
        }
    }

    // handle subscriber interval 1m
    @Scheduled(fixedDelay = 60000)
    public void subscriber1m() {
        logger.info("scheduler 1m");
        List<Subscriber> subscribers = telegramService.getSubscribers("1m");

        for (Subscriber subscriber : subscribers) {
            sendTextMessage(subscriber.getChatId(), telegramService.createLatestLocomotiveSummaryMessage());
        }
    }

    // handle subscriber interval 15m
    @Scheduled(fixedDelay = 900000)
    public void subscriber15m() {
        logger.info("scheduler 15m");
        List<Subscriber> subscribers = telegramService.getSubscribers("15m");

        for (Subscriber subscriber : subscribers) {
            sendTextMessage(subscriber.getChatId(), telegramService.createLatestLocomotiveSummaryMessage());
        }
    }

}
