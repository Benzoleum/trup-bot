package com.shashla.trup_bot.cache;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class CacheUser {

    @Id
    private Long userId;

    private String username;

    private int messageCount;

    public CacheUser(long userId, String username, int messageCount) {
        this.userId = userId;
        this.username = username;
        this.messageCount = messageCount;
    }

    public void incrementMessageCount() {
        this.messageCount++;
    }
}