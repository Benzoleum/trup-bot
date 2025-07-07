package com.shashla.trup_bot.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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