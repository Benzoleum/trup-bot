package com.shashla.trup_bot.cache;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CacheUserRepository extends CrudRepository<CacheUser, Long> {
}
