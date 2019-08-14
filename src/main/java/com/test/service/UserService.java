package com.test.service;

import com.test.dao.ignite.UserRepository;
import com.test.pojo.User;
import com.test.utils.SnowflakeIdUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.cache.CacheAtomicityMode;
import org.apache.ignite.configuration.CacheConfiguration;
import org.apache.ignite.transactions.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private Ignite ignite;

    private SnowflakeIdUtils snowflakeIdUtils = new SnowflakeIdUtils(0, 0);

    public void insert(String name, Integer age, String sex) {
        Transaction tx = this.ignite.transactions().txStart();
        try {
            User user = new User();
            user.setId(snowflakeIdUtils.nextId());
            user.setName(name);
            user.setAge(age);
            user.setSex(sex);
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            this.userRepository.save(user.getId(), user);
//            int i = 1/0;
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
    }

    public List<User> findByPage(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        return this.userRepository.findByPage(pageable);
    }

    public void clear() {
        Transaction tx = this.ignite.transactions().txStart();
        try {
            this.userRepository.deleteAll();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
    }

    public void transactionTest() {
        CacheConfiguration cacheCfg = new CacheConfiguration();
        cacheCfg.setName("testCache");
        cacheCfg.setAtomicityMode(CacheAtomicityMode.TRANSACTIONAL);
        IgniteCache testCache = ignite.getOrCreateCache(cacheCfg);

        Transaction tx = this.ignite.transactions().txStart();
        try {
            testCache.put("test1", "world");
            testCache.put("test2", "李四");
            int i = 1/0;
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            tx.rollback();
        }
        System.out.println("=====>" + testCache.get("test1"));
        System.out.println("=====>" + testCache.get("test2"));
    }
}
