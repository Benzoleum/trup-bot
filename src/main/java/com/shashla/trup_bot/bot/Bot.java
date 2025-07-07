package com.shashla.trup_bot.bot;

import com.shashla.trup_bot.config.BotConfigProperties;
import com.shashla.trup_bot.service.CacheUserService;
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
    private final CacheUserService cacheUserService;

    @Autowired
    public Bot(BotConfigProperties botConfigProperties, CacheUserService cacheUserService) {
        super(botConfigProperties.getBotToken());
        this.botConfigProperties = botConfigProperties;
        this.cacheUserService = cacheUserService;
    }

    @Override
    public void onUpdateReceived(Update update) {
        long chatId = 0;
        if (update != null && update.hasMessage()) {
            chatId = update.getMessage().getChatId();
            if (update.hasMessage()) {
                if (chatId == Long.parseLong(botConfigProperties.getPersonalChatId())) {
                    var msg = update.getMessage();
                    var user = msg.getFrom();
                    Long userId = user.getId();
                    String username = user.getUserName();

                    cacheUserService.insertOrUpdateUserInCache(userId, username);
                }
            }
        } else {
            logger.info("Received update from unknown chat: " + chatId + ". Ignoring.");
        }
    }


    @Override
    public String getBotUsername() {
        return botConfigProperties.getBotName();
    }
}
