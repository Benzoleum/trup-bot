package com.shashla.trup_bot.user;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "bot_user")
public class User {

    @Id
    private Long userId;

    private String username;
    private String nickname;
    private int messageCount;

    public User(Long userId, String username, int messageCount) {
        this.userId = userId;
        this.username = username;
        this.messageCount = messageCount;
    }

    public void incrementMessageCount() {
        this.messageCount++;
    }

}