package com.shashla.trup_bot.config;

import com.shashla.trup_bot.db.UserRepository;
import com.shashla.trup_bot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UserCacheConfig {
    private static final Logger logger = LoggerFactory.getLogger(UserCacheConfig.class);

    UserService userService;
    UserRepository userRepository;

    @Autowired
    public UserCacheConfig(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
        syncCacheWithDbAtStartup();
    }

    public void syncCacheWithDbAtStartup() {
        logger.info("syncing user cache with db at startup");
        userService.syncCacheWithDb(userRepository.findAll());
    }

}
