package com.shashla.trup_bot.service;

import com.shashla.trup_bot.db.UserRepository;
import com.shashla.trup_bot.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void addUserToCache(User user) {
        userRepository.save(user);
    }

    public void incrementMessageCountInCache(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.incrementMessageCount();
            userRepository.save(user); // Save the updated user
        }
    }

    public Iterable<User> getUserCache() {
        return userRepository.findAll();
    }
}