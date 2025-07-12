package com.shashla.trup_bot.bot;

import com.shashla.trup_bot.config.BotConfigProperties;
import com.shashla.trup_bot.service.LogSenderService;
import com.shashla.trup_bot.service.StatusMessageService;
import com.shashla.trup_bot.service.UserService;
import com.shashla.trup_bot.service.UserStatsMessageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class Bot extends TelegramLongPollingBot {

    private static final Logger logger = LoggerFactory.getLogger(Bot.class);

    private final BotConfigProperties botConfigProperties;
    private final UserService userService;
    private final LogSenderService logSenderService;
    private final StatusMessageService statusMessageService;
    private final UserStatsMessageService userStatsMessageService;

    @Autowired
    public Bot(BotConfigProperties botConfigProperties, UserService userService, LogSenderService logSenderService, StatusMessageService statusMessageService, UserStatsMessageService userStatsMessageService) {
        super(botConfigProperties.getBotToken());
        this.botConfigProperties = botConfigProperties;
        this.userService = userService;
        this.logSenderService = logSenderService;
        this.statusMessageService = statusMessageService;
        this.userStatsMessageService = userStatsMessageService;
        logger.info("bot initialized created");
    }

    @Override
    public void onUpdateReceived(Update update) {
        long chatId = 0;
        if (update != null && update.hasMessage()) {
            chatId = update.getMessage().getChatId();
            if (chatId == Long.parseLong(botConfigProperties.getGcChatId())) {
                updateFromShashla(update);
            } else if (chatId == Long.parseLong(botConfigProperties.getPersonalChatId())) {
                updateFromPersonalChat(update);
            }
        } else {
            logger.trace("received update without a message");
        }
    }

    public void updateFromShashla(Update update) {
        var msg = update.getMessage();
        var user = msg.getFrom();
        Long userId = user.getId();
        String username = user.getUserName();
        if (msg.hasText()) {
            if (msg.getText().length() > 1) {
                userService.updateOrCreateUserInCache(userId, username);
            }
        } else {
            userService.updateOrCreateUserInCache(userId, username);
        }
    }

    public void updateFromPersonalChat(Update update) {
        var msg = update.getMessage();

        if (msg.getText().equals("/log")) {
            logSenderService.sendLogFile();
        }

        if (msg.getText().equals("/status")) {
            statusMessageService.sendStatusMessage();
        }

        if (msg.getText().equals("/stats")) {
            userStatsMessageService.sendUserStatsMessage();
        }
    }


    @Override
    public String getBotUsername() {
        return botConfigProperties.getBotName();
    }
}
