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
    private String nickname;

    public CacheUser(long userId, String username, int messageCount) {
        this.userId = userId;
        this.username = username;
        this.messageCount = messageCount;
        resolveNickname(username);
    }

    public void incrementMessageCount() {
        this.messageCount++;
    }

    public void resolveNickname(String username) {
        if (username.equals("Ayan_A_B")) {
            this.nickname = "Величайший";
        } else if (username.equals("Timur996")) {
            this.nickname = "Тимур";
        } else if (username.equals("bfaiziev")) {
            this.nickname = "Бахадур";
        } else if (username.equals("V3034V")) {
            this.nickname = "Володя";
        } else if (username.equals("benzoleum")) {
            this.nickname = "Лук";
        } else {
            this.nickname = "not_recognized";
        }
    }
}