package com.shashla.trup_bot.bot;

import com.shashla.trup_bot.config.BotConfigProperties;
import com.shashla.trup_bot.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

@Component
public class Bot extends TelegramLongPollingBot {

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
        if (update != null) {
            long chatId = update.getMessage().getChatId();
            if (update.hasMessage() && chatId == Long.parseLong(botConfigProperties.getPersonalChatId())) {
                var msg = update.getMessage();
                var user = msg.getFrom();
                Long userId = user.getId();
                String username = user.getUserName();

                userService.insertOrUpdateUserInCache(userId, username);
            }
        }
    }

    @Override
    public String getBotUsername() {
        return botConfigProperties.getBotName();
    }
}
