package com.test.dao.ignite;

import com.test.pojo.User;
import org.apache.ignite.springdata20.repository.IgniteRepository;
import org.apache.ignite.springdata20.repository.config.Query;
import org.apache.ignite.springdata20.repository.config.RepositoryConfig;
import org.springframework.data.domain.Pageable;

import javax.cache.Cache;
import java.util.List;

@RepositoryConfig(cacheName = "userCache")
public interface UserRepository extends IgniteRepository<User, Long> {

    @Query(value = "select * from User")
    List<User> findByPage(Pageable pageable);

    Cache.Entry<Long, User> findTopById(Long id);
}
