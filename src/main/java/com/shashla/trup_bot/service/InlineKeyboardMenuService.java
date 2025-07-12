package com.shashla.trup_bot.service;

import com.shashla.trup_bot.config.BotConfigProperties;
import com.shashla.trup_bot.utils.BotManagementConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.DefaultAbsSender;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.List;

@Service
public class InlineKeyboardMenuService extends DefaultAbsSender {

    private static final Logger logger = LoggerFactory.getLogger(InlineKeyboardMenuService.class);
    private BotConfigProperties botConfigProperties;

    protected InlineKeyboardMenuService(DefaultBotOptions options, String botToken) {
        super(options, botToken);
    }

    @Autowired
    public InlineKeyboardMenuService(BotConfigProperties botConfigProperties) {
        this(new DefaultBotOptions(), botConfigProperties.getBotToken());
        this.botConfigProperties = botConfigProperties;
    }

    private InlineKeyboardMarkup buildMenu() {
        InlineKeyboardMarkup keyboardM1;

        var status = InlineKeyboardButton.builder().text("Bot status").callbackData(BotManagementConstants.STATUS_COMMAND).build();
        var log = InlineKeyboardButton.builder().text("Get bot log").callbackData(BotManagementConstants.LOG_COMMAND).build();
        var stats = InlineKeyboardButton.builder().text("Get user stats").callbackData(BotManagementConstants.USER_STATS_COMMAND).build();

        keyboardM1 = InlineKeyboardMarkup.builder()
                .keyboardRow(List.of(status))
                .keyboardRow(List.of(log))
                .keyboardRow(List.of(stats))
                .build();

        return keyboardM1;
    }

    public void sendMenu() {
        SendMessage sm = SendMessage
                .builder()
                .chatId(botConfigProperties.getPersonalChatId())
                .parseMode("HTML")
                .text("Bot management")
                .replyMarkup(buildMenu())
                .build();

        try {
            execute(sm);
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }

}
