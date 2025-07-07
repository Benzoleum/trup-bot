package com.shashla.trup_bot.service;

import com.shashla.trup_bot.cache.CacheUser;
import com.shashla.trup_bot.cache.CacheUserRepository;
import com.shashla.trup_bot.db.UserRepository;
import com.shashla.trup_bot.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class CacheUserService {

    private static final Logger logger = LoggerFactory.getLogger(CacheUserService.class);

    private final CacheUserRepository cacheUserRepository;
    private final UserRepository userRepository;

    public CacheUserService(CacheUserRepository cacheUserRepository, UserRepository userRepository) {
        this.cacheUserRepository = cacheUserRepository;
        this.userRepository = userRepository;
    }

    public CacheUser saveNewUserToCache(long id, String username) {
        logger.trace("registering new user in cache: " + id + " - " + username);
        return cacheUserRepository.save(new CacheUser(id, username, 1));
    }

    public CacheUser updateExistingUserInCache(long id) {
        CacheUser cacheUser = cacheUserRepository.findById(id).get();
        cacheUser.incrementMessageCount();
        logger.trace("updating user in cache: " + id);
        return cacheUserRepository.save(cacheUser);
    }

    public void insertOrUpdateUserInCache(long id, String username) {
        if (cacheUserRepository.existsById(id)) {
            updateExistingUserInCache(id);
        } else {
            saveNewUserToCache(id, username);
        }
    }

    public Iterable<CacheUser> getAllCacheUsers() {
        return cacheUserRepository.findAll();
    }

    public void persistCacheToDB() {
        Iterable<CacheUser> cachedUsers = cacheUserRepository.findAll();
        for (CacheUser cacheUser : cachedUsers) {
            User user = new User(cacheUser.getUserId(), cacheUser.getUsername(), cacheUser.getMessageCount());
            userRepository.save(user);
        }
    }
}