package com.test.dao.jdbc;

import com.test.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteException;
import org.apache.ignite.cache.store.CacheStoreAdapter;
import org.apache.ignite.lifecycle.LifecycleAware;
import org.apache.ignite.resources.IgniteInstanceResource;
import org.apache.ignite.resources.SpringResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import javax.cache.Cache;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;

@Slf4j
public class UserCacheStore extends CacheStoreAdapter<Long, User> implements LifecycleAware {

    private JdbcTemplate jdbcTemplate;

    @IgniteInstanceResource
    private Ignite ignite;

    @Override
    public User load(Long aLong) throws CacheLoaderException {
        log.info("load--------------------------" + jdbcTemplate);
        log.info("load-------------ignite-------------" + ignite);
        return null;
    }

    @Override
    public void write(Cache.Entry<? extends Long, ? extends User> entry) throws CacheWriterException {
        log.info("write--------------------------" + jdbcTemplate);
        log.info("write-------------ignite-------------" + ignite);
    }

    @Override
    public void delete(Object o) throws CacheWriterException {
        log.info("delete--------------------------" + jdbcTemplate);
    }

    @Override
    public void start() throws IgniteException {
        log.info("start--------------------------" + jdbcTemplate);
        log.info("start--------------ignite------------" + ignite);
    }

    @Override
    public void stop() throws IgniteException {
        log.info("stop--------------------------" + jdbcTemplate);
    }
}
