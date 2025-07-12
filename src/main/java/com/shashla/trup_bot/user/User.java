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
    private int messageCount;
    //TODO implement trup count notification msg
    private int trupCount;
    private String nickname;

    public User(Long userId, String username, int messageCount) {
        this.userId = userId;
        this.username = username;
        this.messageCount = messageCount;
        resolveNickname(username);
    }

    private void resolveNickname(String username) {
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

    public void incrementMessageCount() {
        this.messageCount++;
    }

    public void resetMessageCount() {
        this.messageCount = 0;
    }

    public void incrementTrupCount() {
        this.trupCount++;
    }
}