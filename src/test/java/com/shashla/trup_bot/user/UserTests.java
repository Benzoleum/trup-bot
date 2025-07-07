package com.shashla.trup_bot.user;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTests {
    static User john;
    static User timur;
    static User baha;
    static User vova;
    static User luk;

    @BeforeAll
    public static void setUp() {
        john = new User(1L, "Ayan_A_B", 0);
        timur = new User(2L, "Timur996", 0);
        baha = new User(3L, "bfaiziev", 0);
        vova = new User(4L, "V3034V", 0);
        luk = new User(5L, "benzoleum", 0);
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
    public void shouldResolveNicknameFromUsername() {
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
