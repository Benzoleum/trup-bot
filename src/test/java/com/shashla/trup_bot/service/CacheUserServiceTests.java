package com.shashla.trup_bot.service;

import com.shashla.trup_bot.cache.CacheUser;
import com.shashla.trup_bot.cache.CacheUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CacheUserServiceTests {

    @Autowired
    private CacheUserRepository cacheUserRepository;

    @Autowired
    private CacheUserService cacheUserService;

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void shouldSaveAndRetrieveUser() {
        cacheUserService.saveNewUserToCache(1L, "Test");

        cacheUserService.getAllCacheUsers();

    }
}
