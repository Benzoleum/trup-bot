package com.shashla.trup_bot.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class PropertiesResolverTest {

    @Autowired
    private BotConfigProperties botConfigProperties;

    private static String botToken;
    private static String botName;
    private static String gcChatId;
    private static String personalChatId;

    @BeforeEach
    public void setUp() {
        botToken = botConfigProperties.getBotToken();
        botName = botConfigProperties.getBotName();
        gcChatId = botConfigProperties.getGcChatId();
        personalChatId = botConfigProperties.getPersonalChatId();
    }

    @Test
    public void shouldResolveToken() {
        assert botToken.equals("7373436976:AAFKLyJNWjczrvyQ-TWNGxPAyrP44gqGwsI");
    }

    @Test
    public void shouldResolveBotName() {
        assert botName.equals("weekly_message_counter_bot");
    }

    @Test
    public void shouldResolveGcChatId() {
        assert gcChatId.equals("-1001464721367");
    }

    @Test
    public void shouldResolvePersonalChatId() {
        assert personalChatId.equals("59612513");
    }
}
