package com.test.ignite.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class JdbcTemplateService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void createTable() {
        this.jdbcTemplate.execute("drop table if exists tb_user");
        this.jdbcTemplate.execute("create table tb_user (" +
                "id bigint(20) auto_increment, " +
                "name varchar(50), " +
                "age int(11), " +
                "sex varchar(10), " +
                "create_time timestamp, " +
                "update_time timestamp, " +
                "primary key (id)) " +
                "ENGINE=InnoDB DEFAULT CHARSET=utf8mb4");
    }

    public void insertData() {
        this.jdbcTemplate.execute("insert into tb_user(id, name, age, sex, create_time, update_time) values (1, '张三', 20, '男', '2019-08-09 06:17:33', '2019-08-09 06:17:33')");
        this.jdbcTemplate.execute("insert into tb_user(id, name, age, sex, create_time, update_time) values (2, '李四', 25, '男', '2019-08-09 06:17:33', '2019-08-09 06:17:33')");
        this.jdbcTemplate.execute("insert into tb_user(id, name, age, sex, create_time, update_time) values (3, '王五', 22, '女', '2019-08-09 06:17:33', '2019-08-09 06:17:33')");
        this.jdbcTemplate.execute("insert into tb_user(id, name, age, sex, create_time, update_time) values (4, '赵六', 19, '女', '2019-08-09 06:17:33', '2019-08-09 06:17:33')");
    }

    public List<Map<String, Object>> queryData() {
        return this.jdbcTemplate.queryForList("select * from tb_user limit 1, 2");
    }
}
