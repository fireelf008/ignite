package com.test.dao.db;

import com.test.dao.mapper.UserMapper;
import com.test.pojo.User;
import com.test.utils.ApplicationContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.IgniteException;
import org.apache.ignite.cache.store.CacheStoreAdapter;
import org.apache.ignite.lang.IgniteBiInClosure;
import org.apache.ignite.lifecycle.LifecycleAware;

import javax.cache.Cache;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class UserCacheStore extends CacheStoreAdapter<Long, User> implements LifecycleAware {

    private UserMapper userMapper;

    @Override
    public User load(Long id) throws CacheLoaderException {
        this.initUserMapper();
        return this.userMapper.findById(id);
    }

    @Override
    public void write(Cache.Entry<? extends Long, ? extends User> entry) throws CacheWriterException {
        User user = entry.getValue();

        this.initUserMapper();
        int count = this.userMapper.update(user);
        if (0 == count) {
            this.userMapper.insert(user);
        }
    }

    @Override
    public void delete(Object id) throws CacheWriterException {
        this.initUserMapper();
        this.userMapper.delete(Long.parseLong(id.toString()));
    }

    @Override
    public void loadCache(IgniteBiInClosure<Long, User> clo, Object... args) {
        final AtomicInteger cnt = new AtomicInteger();

        this.initUserMapper();
        List<User> userList = this.userMapper.findAll();
        userList.forEach(u -> {
            clo.apply(u.getId(), u);
            cnt.incrementAndGet();
        });
    }

    @Override
    public void start() throws IgniteException {

    }

    @Override
    public void stop() throws IgniteException {

    }

    private void initUserMapper() {
        if (null == this.userMapper) {
            this.userMapper = ApplicationContextUtils.getBean(UserMapper.class);
        }
    }
}
