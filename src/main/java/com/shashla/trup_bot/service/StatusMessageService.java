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
public class StatusMessageService extends DefaultAbsSender {
    private static final Logger logger = LoggerFactory.getLogger(StatusMessageService.class);

    UserService userService;
    private BotConfigProperties botConfigProperties;

    @Autowired
    public StatusMessageService(BotConfigProperties botConfigProperties, UserService userService) {
        super(new DefaultBotOptions(), botConfigProperties.getBotToken());
        this.userService = userService;
        this.botConfigProperties = botConfigProperties;
    }

    protected StatusMessageService(DefaultBotOptions options, String botToken) {
        super(options, botToken);
    }

    public void sendStatusMessage() {
        try {
            SendMessage message = new SendMessage();
            message.setChatId(botConfigProperties.getPersonalChatId());
            message.setText("I am alive");
            execute(message);
        } catch (TelegramApiException e) {
            logger.error("failed to send the status message", e);
            throw new RuntimeException(e);
        }
    }
}
