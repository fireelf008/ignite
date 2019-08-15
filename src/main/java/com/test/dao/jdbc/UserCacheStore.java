package com.test.dao.jdbc;

import com.test.pojo.User;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteException;
import org.apache.ignite.cache.store.CacheStoreAdapter;
import org.apache.ignite.lang.IgniteBiInClosure;
import org.apache.ignite.lifecycle.LifecycleAware;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.cache.Cache;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class UserCacheStore extends CacheStoreAdapter<Long, User> implements LifecycleAware {

    private NamedParameterJdbcTemplate jdbcTemplate;

    @IgniteInstanceResource
    private Ignite ignite;
    @Override
    public User load(Long id) throws CacheLoaderException {
        String sql = "select * from tb_user where id = :id";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);

        User user = this.jdbcTemplate.queryForObject(sql, paramMap, new BeanPropertyRowMapper<User>(User.class));
        return user;
    }

    @Override
    public void write(Cache.Entry<? extends Long, ? extends User> entry) throws CacheWriterException {
        User user = entry.getValue();

        String sql = "update tb_user set name = :name, age = :age, sex = :sex, create_time = :create_time, update_time = :update_time where id = :id";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", user.getId());
        paramMap.put("name", user.getName());
        paramMap.put("age", user.getAge());
        paramMap.put("sex", user.getSex());
        paramMap.put("create_time", user.getCreateTime());
        paramMap.put("update_time", user.getUpdateTime());

        int count = this.jdbcTemplate.update(sql, paramMap);
        if (0 == count) {
            sql = "insert into tb_user (id, name, age, sex, create_time, update_time) values (:id, :name, :age, :sex, :create_time, :update_time)";
            this.jdbcTemplate.update(sql, paramMap);
        }
    }

    @Override
    public void delete(Object id) throws CacheWriterException {
        String sql = "delete from tb_user where id = :id";
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("id", id);
        this.jdbcTemplate.update(sql, paramMap);
    }

    @Override
    public void loadCache(IgniteBiInClosure<Long, User> clo, Object... args) {
        if (null != args && 0 != args.length && null != args[0]) {
            int entryCnt = (Integer)args[0];
            final AtomicInteger cnt = new AtomicInteger();

            String sql = "select * from tb_user";
            List<User> userList  = this.jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class));
            userList.forEach(u -> {
                clo.apply(u.getId(), u);
                cnt.incrementAndGet();
            });
            System.out.println(">>> Loaded " + cnt + " values into cache.");
        }
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
        log.info("start--------------------------" + jdbcTemplate);
        log.info("start--------------ignite------------" + ignite);
    }

    @Override
    public void stop() throws IgniteException {
        log.info("stop--------------------------" + jdbcTemplate);
    }
}
