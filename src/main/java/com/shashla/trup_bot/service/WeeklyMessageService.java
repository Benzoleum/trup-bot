package com.shashla.trup_bot.service;

import com.shashla.trup_bot.config.BotConfigProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class WeeklyMessageService extends DefaultAbsSender {
    private static final Logger logger = LoggerFactory.getLogger(WeeklyMessageService.class);

    TrupService trupService;
    private BotConfigProperties botConfigProperties;

    @Autowired
    public WeeklyMessageService(BotConfigProperties botConfigProperties, TrupService trupService) {
        this(new DefaultBotOptions(), botConfigProperties.getBotToken());
        this.trupService = trupService;
        this.botConfigProperties = botConfigProperties;
    }

    protected WeeklyMessageService(DefaultBotOptions options, String botToken) {
        super(options, botToken);
    }

    @Scheduled(cron = "0 0 10 ? * SUN")
    private void sendTestMsg() {
        try {
            SendMessage message = new SendMessage();
            message.setChatId(botConfigProperties.getPersonalChatId());

            message.setText("В Лондоне воскресенье, 10 утра.");
            execute(message);

            message.setText(trupService.prepareStatsForAWeek());
            execute(message);

            message.setText(trupService.chooseTrup());
            execute(message);

        } catch (TelegramApiException e) {
            logger.error("Failed to send the message to chat", e);
            throw new RuntimeException(e);
        }
    }
}
