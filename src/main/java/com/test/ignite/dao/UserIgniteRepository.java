package com.test.ignite.dao;

import com.test.ignite.pojo.User;
import org.apache.ignite.springdata20.repository.IgniteRepository;
import org.apache.ignite.springdata20.repository.config.RepositoryConfig;
import org.springframework.stereotype.Repository;

//@Repository
@RepositoryConfig(cacheName = "userCache")
public interface UserIgniteRepository extends IgniteRepository<User, Long> {

}
