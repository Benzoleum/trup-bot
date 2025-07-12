package com.shashla.trup_bot.db;

import com.shashla.trup_bot.user.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}