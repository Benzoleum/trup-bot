package com.shashla.trup_bot.cache;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class CacheRepoTests {

    @Autowired
    private CacheUserRepository cacheUserRepository;

    @Test
    public void shouldSaveAndRetrieveUser() {
        CacheUser user = new CacheUser(1L, "Test", 0);
        cacheUserRepository.save(user);

        Optional<CacheUser> retrievedUser = cacheUserRepository.findById(1L);

        assertTrue(retrievedUser.isPresent());
        assertEquals("Test", retrievedUser.get().getUsername());
    }

    @Test
    public void shouldUpdateExistingUser() {
        CacheUser user = new CacheUser(1L, "Test", 1);
        cacheUserRepository.save(user);

        user.incrementMessageCount();
        cacheUserRepository.save(user);

        Optional<CacheUser> retrievedUser = cacheUserRepository.findById(1L);

        assertTrue(retrievedUser.isPresent());
        assertEquals(2, retrievedUser.get().getMessageCount());
    }
}