package com.shashla.trup_bot.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

public class UserServiceTests {

    private UserService userService = new UserService();

    @BeforeEach
    public void setUp() {
        userService.updateOrCreateUserInCache(1L, "Test1");
        userService.updateOrCreateUserInCache(1L, "Test1");
        userService.updateOrCreateUserInCache(1L, "Test1");
        userService.updateOrCreateUserInCache(1L, "Test1");
        userService.updateOrCreateUserInCache(1L, "Test1");
        userService.updateOrCreateUserInCache(2L, "Test2");
        userService.updateOrCreateUserInCache(2L, "Test2");
        userService.updateOrCreateUserInCache(2L, "Test2");
        userService.updateOrCreateUserInCache(3L, "Test3");
        userService.updateOrCreateUserInCache(3L, "Test3");
        userService.updateOrCreateUserInCache(3L, "Test3");
        userService.updateOrCreateUserInCache(3L, "Test3");
        userService.updateOrCreateUserInCache(3L, "Test3");
        userService.updateOrCreateUserInCache(3L, "Test3");
        userService.updateOrCreateUserInCache(4L, "Test4");
        userService.updateOrCreateUserInCache(5L, "Test5");
        userService.updateOrCreateUserInCache(5L, "Test5");
        userService.updateOrCreateUserInCache(5L, "Test5");
        userService.updateOrCreateUserInCache(5L, "Test5");
        userService.updateOrCreateUserInCache(5L, "Test5");
        userService.updateOrCreateUserInCache(5L, "Test5");
        userService.updateOrCreateUserInCache(5L, "Test5");
        userService.updateOrCreateUserInCache(5L, "Test5");
        userService.updateOrCreateUserInCache(5L, "Test5");

    }

    @Test
    public void shouldReturnCorrectUserCount() {
        assert userService.getAllCacheUsers().size() == 5;
    }

    @Test
    public void shouldReturnCorrectTrup() {
        assert userService.getTrup().getUsername().equals("Test4");
    }

    @Test
    public void shouldResetUserMessageCountInCache() {
        userService.resetUserMessageCountInCache();
        assert userService.getAllCacheUsers().stream().allMatch(user -> user.getMessageCount() == 0);
    }
}
