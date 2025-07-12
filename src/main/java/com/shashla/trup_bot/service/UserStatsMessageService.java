package com.shashla.trup_bot.service;

import com.shashla.trup_bot.config.BotConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class UserStatsMessageService extends DefaultAbsSender {
    private static final Logger logger = LoggerFactory.getLogger(UserStatsMessageService.class);

    UserService userService;
    private BotConfigProperties botConfigProperties;

    @Autowired
    public UserStatsMessageService(BotConfigProperties botConfigProperties, UserService userService) {
        super(new DefaultBotOptions(), botConfigProperties.getBotToken());
        this.userService = userService;
        this.botConfigProperties = botConfigProperties;
    }

    protected UserStatsMessageService(DefaultBotOptions options, String botToken) {
        super(options, botToken);
    }

    public void sendUserStatsMessage() {
        try {
            SendMessage message = new SendMessage();
            message.setChatId(botConfigProperties.getPersonalChatId());
            message.setText(getUserStats());
            execute(message);
        } catch (TelegramApiException e) {
            logger.error("failed to send the status message", e);
            throw new RuntimeException(e);
        }
    }

    private String getUserStats() {
        StringBuilder sb = new StringBuilder();
        userService.getAllCacheUsers().forEach(user -> {
            sb.append(user.getNickname() + ": " + user.getMessageCount() + "\n");
        });

        if (sb.length() != 0) {
            return sb.toString();
        } else {
            return "no users registered yet";
        }
    }
}
