package com.shashla.trup_bot.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "bot-config")
@Getter
@Setter
public class BotConfigProperties {

    private String botToken;
    private String botName;
    private String gcChatId;
    private String personalChatId;

}