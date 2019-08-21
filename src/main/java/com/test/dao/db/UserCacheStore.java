package com.test.dao.db;

import com.test.dao.mapper.UserMapper;
import com.test.pojo.User;
import com.test.utils.ApplicationContextUtils;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.IgniteException;
import org.apache.ignite.cache.store.CacheStoreAdapter;
import org.apache.ignite.lang.IgniteBiInClosure;
import org.apache.ignite.lifecycle.LifecycleAware;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.cache.Cache;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;
import javax.sql.DataSource;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class UserCacheStore extends CacheStoreAdapter<Long, User> implements LifecycleAware {

    private NamedParameterJdbcTemplate jdbcTemplate;

    private UserMapper userMapper;

    @Override
    public User load(Long id) throws CacheLoaderException {
        if (null == this.userMapper) {
            this.userMapper = ApplicationContextUtils.getBean(UserMapper.class);
        }

        return this.userMapper.findById(id);
    }

    @Override
    public void write(Cache.Entry<? extends Long, ? extends User> entry) throws CacheWriterException {
        User user = entry.getValue();

        if (null == this.userMapper) {
            this.userMapper = ApplicationContextUtils.getBean(UserMapper.class);
        }

        int count = this.userMapper.update(user);
        if (0 == count) {
            this.userMapper.insert(user);
        }
    }

    @Override
    public void delete(Object id) throws CacheWriterException {
        if (null == this.userMapper) {
            this.userMapper = ApplicationContextUtils.getBean(UserMapper.class);
        }

        this.userMapper.delete(Long.parseLong(id.toString()));
    }

    @Override
    public void loadCache(IgniteBiInClosure<Long, User> clo, Object... args) {
        final AtomicInteger cnt = new AtomicInteger();

        String sql = "select * from tb_user";
        List<User> userList  = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class));
        userList.forEach(u -> {
            clo.apply(u.getId(), u);
            cnt.incrementAndGet();
        });
    }

    @Override
    public void start() throws IgniteException {
        HikariConfig hikariConfig = new HikariDataSource();
        hikariConfig.setDriverClassName("com.mysql.jdbc.Driver");
        hikariConfig.setJdbcUrl("jdbc:mysql://localhost:3306/test?useUnicode=true&character=utf8mb4&useSSL=false&serverTimezone=GMT");
        hikariConfig.setUsername("root");
        hikariConfig.setPassword("root");
        DataSource dataSource = new HikariDataSource(hikariConfig);
        if (null == this.jdbcTemplate) {
            this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
        }
    }

    @Override
    public void stop() throws IgniteException {

    }
}
