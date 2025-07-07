package com.shashla.trup_bot.service;

import com.shashla.trup_bot.cache.CacheUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrupService {

    private final CacheUserRepository cacheUserRepository;

    @Autowired
    public TrupService(CacheUserRepository cacheUserRepository) {
        this.cacheUserRepository = cacheUserRepository;
    }


}
