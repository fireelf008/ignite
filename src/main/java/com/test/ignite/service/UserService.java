package com.test.ignite.service;

import com.test.ignite.dao.UserRepository;
import com.test.ignite.pojo.User;
import com.test.ignite.utils.SnowflakeIdUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private SnowflakeIdUtils snowflakeIdUtils = new SnowflakeIdUtils(0, 0);

    public void insert(String name, Integer age, String sex) {
        User user = new User();
        user.setId(snowflakeIdUtils.nextId());
        user.setName(name);
        user.setAge(age);
        user.setSex(sex);
        user.setCreateTime(new Date());
        user.setUpdateTime(new Date());
        this.userRepository.save(user.getId(), user);
    }

    public List<User> findByPage(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        return this.userRepository.findByPage(pageable);
    }

    public List<User> findByAgeGreaterThanEqual() {
//        return this.userRepository.findByAgeGreaterThanEqual(20);
        return null;
    }
}
