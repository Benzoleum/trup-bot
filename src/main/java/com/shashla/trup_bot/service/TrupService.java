package com.shashla.trup_bot.service;

import com.shashla.trup_bot.db.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TrupService {

    private final UserRepository userRepository;

    @Autowired
    public TrupService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


}
