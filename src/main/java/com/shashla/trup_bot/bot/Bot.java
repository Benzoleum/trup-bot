package com.shashla.trup_bot.bot;

import com.shashla.trup_bot.config.BotConfigProperties;
import com.shashla.trup_bot.service.*;
import com.shashla.trup_bot.utils.BotManagementConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.AnswerCallbackQuery;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class Bot extends TelegramLongPollingBot {

    private static final Logger logger = LoggerFactory.getLogger(Bot.class);

    private final BotConfigProperties botConfigProperties;
    private final UserService userService;
    private final LogSenderService logSenderService;
    private final StatusMessageService statusMessageService;
    private final UserStatsMessageService userStatsMessageService;
    private final InlineKeyboardMenuService inlineKeyboardMenuService;

    @Autowired
    public Bot(BotConfigProperties botConfigProperties, UserService userService, LogSenderService logSenderService, StatusMessageService statusMessageService, UserStatsMessageService userStatsMessageService, InlineKeyboardMenuService inlineKeyboardMenuService) {
        super(botConfigProperties.getBotToken());
        this.botConfigProperties = botConfigProperties;
        this.userService = userService;
        this.logSenderService = logSenderService;
        this.statusMessageService = statusMessageService;
        this.userStatsMessageService = userStatsMessageService;
        this.inlineKeyboardMenuService = inlineKeyboardMenuService;
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
            if (update.hasCallbackQuery()) {
                try {
                    menuCallbackFromPersonalChat(update);
                } catch (TelegramApiException e) {
                    throw new RuntimeException(e);
                }
            }
            logger.trace("received update without a message");
        }
    }

    private void updateFromShashla(Update update) {
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

    private void updateFromPersonalChat(Update update) {
        var msg = update.getMessage();
        var txt = msg.getText();
        if (txt.equals("/start")) {
            inlineKeyboardMenuService.sendMenu();
        }
    }

    private void menuCallbackFromPersonalChat(Update update) throws TelegramApiException {
        if (update.getCallbackQuery().getData().equals(BotManagementConstants.STATUS_COMMAND)) {
            statusMessageService.sendStatusMessage();
            answerCallbackQuery(update, "status");
        } else if (update.getCallbackQuery().getData().equals(BotManagementConstants.LOG_COMMAND)) {
            logSenderService.sendLogFile();
            answerCallbackQuery(update, "log");
        } else if (update.getCallbackQuery().getData().equals(BotManagementConstants.USER_STATS_COMMAND)) {
            userStatsMessageService.sendUserStatsMessage();
            answerCallbackQuery(update, "user stats");
        } else {
            throw new TelegramApiException("unknown request");
        }
        logger.info("received a callback query " + update.getCallbackQuery().getData().toString());
    }

    private void answerCallbackQuery(Update update, String text) {
        AnswerCallbackQuery answer = new AnswerCallbackQuery();
        answer.setCallbackQueryId(update.getCallbackQuery().getId());
        answer.setText(text + " request");
        try {
            execute(answer);
        } catch (TelegramApiException e) {
            logger.error("failed to send callback query response", e);
        }
    }


    @Override
    public String getBotUsername() {
        return botConfigProperties.getBotName();
    }
}
