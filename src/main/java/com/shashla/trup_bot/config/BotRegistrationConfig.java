package com.shashla.trup_bot.config;

import com.shashla.trup_bot.bot.Bot;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Configuration
public class BotRegistrationConfig {

    private static final Logger logger = LoggerFactory.getLogger(BotRegistrationConfig.class);

    @Bean
    public TelegramBotsApi telegramBotsApi(Bot bot) throws Exception {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        logger.info("registering bot...");
        telegramBotsApi.registerBot(bot);
        logger.info("bot registered");
        return telegramBotsApi;
    }
}