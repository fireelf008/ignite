package com.test.ignite.dao;

import com.test.ignite.pojo.User;
import org.apache.ignite.springdata20.repository.IgniteRepository;
import org.apache.ignite.springdata20.repository.config.Query;
import org.apache.ignite.springdata20.repository.config.RepositoryConfig;
import org.springframework.data.domain.Pageable;

import java.util.List;

@RepositoryConfig(cacheName = "userCache")
public interface UserRepository extends IgniteRepository<User, Long> {

    @Query(value = "select * from User")
    List<User> findByPage(Pageable pageable);

//    List<User> findByAgeGreaterThanEqual(Integer age);
}
