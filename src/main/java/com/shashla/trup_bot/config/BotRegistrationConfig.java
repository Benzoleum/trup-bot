package com.shashla.trup_bot.config;

import com.shashla.trup_bot.bot.Bot;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Configuration
public class BotRegistrationConfig {

    @Bean
    public TelegramBotsApi telegramBotsApi(Bot bot) throws Exception {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(bot);
        return telegramBotsApi;
    }
}