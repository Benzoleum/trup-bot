package com.shashla.trup_bot.bot;

import com.shashla.trup_bot.config.BotConfigProperties;
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

    @Autowired
    public Bot(BotConfigProperties botConfigProperties, UserService userService) {
        super(botConfigProperties.getBotToken());
        this.botConfigProperties = botConfigProperties;
        this.userService = userService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        long chatId = 0;
        if (update != null && update.hasMessage()) {
            chatId = update.getMessage().getChatId();
            if (update.hasMessage()) {
                if (chatId == Long.parseLong(botConfigProperties.getPersonalChatId()) || chatId == Long.parseLong(botConfigProperties.getGcChatId())) {
                    var msg = update.getMessage();
                    var user = msg.getFrom();
                    Long userId = user.getId();
                    String username = user.getUserName();

                    userService.updateOrCreateUserInCache(userId, username);
                }
            }
        } else {
            logger.info("received update from an unknown chat: " + chatId + ". Ignoring.");
        }
    }


    @Override
    public String getBotUsername() {
        return botConfigProperties.getBotName();
    }
}
