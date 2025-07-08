package com.shashla.trup_bot.service;

import com.shashla.trup_bot.config.BotConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.io.File;

@Service
public class LogSenderService extends DefaultAbsSender {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);
    private BotConfigProperties botConfigProperties;

    @Autowired
    public LogSenderService(BotConfigProperties botConfigProperties) {
        this(new DefaultBotOptions(), botConfigProperties.getBotToken());
        this.botConfigProperties = botConfigProperties;
    }

    protected LogSenderService(DefaultBotOptions options, String botToken) {
        super(options, botToken);
    }

    public void sendLogFile() {
        try {
            logger.info("preparing log file to be sent...");
            SendDocument sendDocument = SendDocument.builder()
                    .chatId(botConfigProperties.getPersonalChatId())
                    .document(new InputFile(new File("logs/application.log")))
                    .build();
            logger.info("sending log file...");
            execute(sendDocument);
        } catch (TelegramApiException e) {
            logger.error("failed to send the log file", e);
            throw new RuntimeException(e);
        }
    }
}
