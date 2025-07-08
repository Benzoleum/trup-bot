package com.shashla.trup_bot.db;

import com.shashla.trup_bot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class PersistCache {
    private static final Logger logger = LoggerFactory.getLogger(PersistCache.class);

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Scheduled(fixedRate = 3600000, initialDelay = 3600000)
    private void persistCache() {
        logger.info("persisting user cache");
        userRepository.saveAll(userService.getAllCacheUsers());
    }
}
