package com.shashla.trup_bot.service;

import com.shashla.trup_bot.config.BotConfigProperties;
import com.shashla.trup_bot.user.User;
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

    UserService userService;
    private BotConfigProperties botConfigProperties;

    @Autowired
    public WeeklyMessageService(BotConfigProperties botConfigProperties, UserService userService) {
        this(new DefaultBotOptions(), botConfigProperties.getBotToken());
        this.userService = userService;
        this.botConfigProperties = botConfigProperties;
    }

    protected WeeklyMessageService(DefaultBotOptions options, String botToken) {
        super(options, botToken);
    }

    @Scheduled(cron = "0 0 10 ? * SUN")
    private void sendOutTrupMessage() {
        try {
            SendMessage message = new SendMessage();
            message.setChatId(botConfigProperties.getPersonalChatId());

            message.setText("В Лондоне воскресенье, 10 утра.");
            execute(message);

            message.setText(weeklyStatsMessage());
            execute(message);

            message.setText(trupMessage());
            execute(message);

        } catch (TelegramApiException e) {
            logger.error("Failed to send the message to chat", e);
            throw new RuntimeException(e);
        }
    }

    protected String weeklyStatsMessage() {
        StringBuilder sb = new StringBuilder();
        for (User user : userService.getAllCacheUsers()) {
            sb.append(user.getNickname()).append(" написал ").append(user.getMessageCount()).append(" сообщений за неделю.\n");
        }
        return sb.toString();
    }

    protected String trupMessage() {
        StringBuilder sb = new StringBuilder();
        User trup = getTrupResetCache();
        sb.append(trup.getNickname() + " написал меньше всех. " + trup.getNickname() + "- труп недели. Поздравляю.");
        return sb.toString();
    }

    private User getTrupResetCache() {
        User trup = userService.getTrup();
        userService.resetUserMessageCountInCache();
        return trup;
    }
}
