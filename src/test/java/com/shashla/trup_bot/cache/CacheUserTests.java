package com.shashla.trup_bot.cache;

import com.shashla.trup_bot.cache.CacheUser;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CacheUserTests {
    static CacheUser john;
    static CacheUser timur;
    static CacheUser baha;
    static CacheUser vova;
    static CacheUser luk;

    @BeforeAll
    public static void setUp() {
        john = new CacheUser(1L, "Ayan_A_B", 0);
        timur = new CacheUser(2L, "Timur996", 0);
        baha = new CacheUser(3L, "bfaiziev", 0);
        vova = new CacheUser(4L, "V3034V", 0);
        luk = new CacheUser(5L, "benzoleum", 0);
    }

    @Test
    public void shouldIncrementMessageCount() {
        john.incrementMessageCount();
        timur.incrementMessageCount();
        baha.incrementMessageCount();
        vova.incrementMessageCount();
        luk.incrementMessageCount();

        assertEquals(1, john.getMessageCount());
        assertEquals(1, timur.getMessageCount());
        assertEquals(1, baha.getMessageCount());
        assertEquals(1, vova.getMessageCount());
        assertEquals(1, luk.getMessageCount());

    }

    @Test
    public void shouldResolveNicknameFromCacheUsername() {
        john.resolveNickname("Ayan_A_B");
        timur.resolveNickname("Timur996");
        baha.resolveNickname("bfaiziev");
        vova.resolveNickname("V3034V");
        luk.resolveNickname("benzoleum");

        assertEquals("Величайший", john.getNickname());
        assertEquals("Тимур", timur.getNickname());
        assertEquals("Бахадур", baha.getNickname());
        assertEquals("Володя", vova.getNickname());
        assertEquals("Лук", luk.getNickname());
    }
}
