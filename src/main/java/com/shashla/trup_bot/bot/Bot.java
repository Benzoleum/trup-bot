package com.shashla.trup_bot.bot;

import com.shashla.trup_bot.config.BotConfigProperties;
import com.shashla.trup_bot.service.TrupService;
import com.shashla.trup_bot.service.UserService;
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
    private final TrupService trupService;

    @Autowired
    public Bot(BotConfigProperties botConfigProperties, UserService userService, TrupService trupService) {
        super(botConfigProperties.getBotToken());
        this.botConfigProperties = botConfigProperties;
        this.userService = userService;
        this.trupService = trupService;
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

    }


    @Override
    public String getBotUsername() {
        return botConfigProperties.getBotName();
    }
}
