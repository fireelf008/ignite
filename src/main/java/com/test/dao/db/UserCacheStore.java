package com.test.dao.db;

import com.test.dao.mapper.UserMapper;
import com.test.pojo.User;
import com.test.utils.ApplicationContextUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.IgniteException;
import org.apache.ignite.cache.store.CacheStoreAdapter;
import org.apache.ignite.cache.store.CacheStoreSession;
import org.apache.ignite.lang.IgniteBiInClosure;
import org.apache.ignite.lifecycle.LifecycleAware;
import org.apache.ignite.resources.CacheStoreSessionResource;
import org.springframework.transaction.TransactionStatus;

import javax.cache.Cache;
import javax.cache.integration.CacheLoaderException;
import javax.cache.integration.CacheWriterException;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j
public class UserCacheStore extends CacheStoreAdapter<Long, User> implements BaseCacheStore, LifecycleAware {

    @CacheStoreSessionResource
    private CacheStoreSession ses;

    UserMapper userMapper = ApplicationContextUtils.getBean(UserMapper.class);

    @Override
    public void sessionEnd(boolean commit) {
        TransactionStatus status = ses.attachment();
        if (status != null && ses.isWithinTransaction()) {
            if (commit) {
                this.transactionManager.commit(status);
            } else {
                this.transactionManager.rollback(status);
            }
        }
    }

    @Override
    public User load(Long id) throws CacheLoaderException {
        return this.userMapper.findById(id);
    }

    @Override
    public void write(Cache.Entry<? extends Long, ? extends User> entry) throws CacheWriterException {
        TransactionStatus status = this.transactionManager.getTransaction(def);
        ses.attach(status);

        User user = entry.getValue();
        int count = this.userMapper.update(user);
        if (0 == count) {
            this.userMapper.insert(user);
        }
    }

    @Override
    public void delete(Object id) throws CacheWriterException {
        TransactionStatus status = this.transactionManager.getTransaction(def);
        ses.attach(status);

        this.userMapper.delete(Long.parseLong(id.toString()));
    }

    @Override
    public void loadCache(IgniteBiInClosure<Long, User> clo, Object... args) {
        final AtomicInteger cnt = new AtomicInteger();

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
}
