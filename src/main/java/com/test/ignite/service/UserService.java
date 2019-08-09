package com.test.ignite.service;

import com.test.ignite.dao.UserIgniteRepository;
import com.test.ignite.dao.UserRepository;
import com.test.ignite.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserIgniteRepository userIgniteRepository;

    public void insert(String name, Integer age, String sex) {
        User user = new User();
        user.setName(name);
        user.setAge(age);
        user.setSex(sex);
//        this.userRepository.save(user);
        this.userRepository.save(user);
    }

    public Page<User> findByPage(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "id");
        return this.userRepository.findAll(pageable);
    }
}
