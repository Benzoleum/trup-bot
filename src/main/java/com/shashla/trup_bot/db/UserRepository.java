package com.shashla.trup_bot.db;

import com.shashla.trup_bot.user.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    /**
     * Increment the trup count for a user with the specified ID
     *
     * @param userId the ID of the user
     * @return the number of rows affected
     */
    @Modifying
    @Transactional
    @Query("UPDATE User u SET u.trupCount = u.trupCount + 1 WHERE u.userId = :userId")
    int incrementTrupCount(@Param("userId") Long userId);

}