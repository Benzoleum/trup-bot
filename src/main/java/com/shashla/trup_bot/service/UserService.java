package com.shashla.trup_bot.service;

import com.shashla.trup_bot.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private Map<Long, User> userCache = new HashMap<>();

    public void updateOrCreateUserInCache(long id, String username) {
        if (!userCache.containsKey(id)) {
            logger.info("registering new user in cache: " + id + " - " + username);
            userCache.put(id, new User(id, username, 1));
        } else {
            logger.trace("updating existing user in cache: " + id + " - " + username + " with message count: " + userCache.get(id).getMessageCount());
            userCache.get(id).incrementMessageCount();
        }
    }

    public List<User> getAllCacheUsers() {
        return userCache.values().stream().toList();
    }

    public void syncCacheWithDb(Iterable<User> users) {
        for (User user : users) {
            logger.info("syncing user from db: " + user.getUsername() + " with message count: " + user.getMessageCount());
            userCache.put(user.getUserId(), user);
        }
    }

    public void resetUserMessageCountInCache() {
        for (User userInCache : userCache.values()) {
            logger.info("resetting user message count in cache: " + userInCache.getUsername());
            userInCache.resetMessageCount();
            logger.info("user message count reset: " + userInCache.getMessageCount());
        }
    }

    public User getTrup() {
        int max = Integer.MAX_VALUE;
        User trup = null;
        for (User user : getAllCacheUsers()) {
            if (user.getMessageCount() < max) {
                max = user.getMessageCount();
                trup = user;
            }
        }
        logger.info("trup: " + trup.getUsername() + " with message count: " + trup.getMessageCount());
        trup.incrementTrupCount();
        return trup;
    }
}