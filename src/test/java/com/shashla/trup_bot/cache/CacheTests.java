package com.shashla.trup_bot.cache;

import com.shashla.trup_bot.bot.Bot;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;

public class CacheTests {

    private static CacheUser test;

    @Mock
    private CacheUserRepository cacheUserRepository;

    @InjectMocks
    private Bot bot;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        test = new CacheUser("Test", 0);
        
        // Mock `cacheUserRepository.save()`
        when(cacheUserRepository.save(test)).thenReturn(test);
    }

    @Test
    public void shouldIncrementMessageCount() {
        cacheUserRepository.save(test);

        assert cacheUserRepository.existsById(1L);
        cacheUserRepository.findById(1L);

        test.incrementMessageCount();

        assert test.getMessageCount() == 1;
    }
}