package com.shashla.trup_bot.service;

import com.shashla.trup_bot.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrupService {

    UserService userService;
    private long trupId = 0;

    @Autowired
    public TrupService(UserService userService) {
        this.userService = userService;
    }

    protected String prepareStatsForAWeek() {
        StringBuilder sb = new StringBuilder();

        int max = Integer.MAX_VALUE;

        for (User user : userService.getAllCacheUsers()) {
            if (user.getMessageCount() < max) {
                max = user.getMessageCount();
                trupId = user.getUserId();
            }
            sb.append(user.getNickname()).append(" написал ").append(user.getMessageCount()).append(" сообщений за неделю.\n");
        }
        return sb.toString();
    }

    protected String chooseTrup() {
        StringBuilder sb = new StringBuilder();
        String trupNickname = userService.getAllCacheUsers().stream().filter(user -> user.getUserId() == trupId).findFirst().get().getNickname();
        sb.append(trupNickname + " написал меньше всех. " + trupNickname + "- труп недели. Поздравляю.");
        return sb.toString();
    }
}
