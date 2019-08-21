package com.test.dao.mapper;

import com.test.pojo.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface UserMapper {

    @Insert("insert into tb_user (id, name, age, sex, create_time, update_time) values (#{id}, #{name}, #{age}, #{sex}, #{createTime}, #{updateTime})")
    int insert(User user);

    @Update("update tb_user set name = #{name}, age = #{age}, sex = #{sex}, create_time = #{createTime}, update_time = #{updateTime} where id = #{id}")
    int update(User user);

    @Delete("delete from tb_user where id = #{id}")
    int delete(Long id);

    @Select("select * from tb_user where id = #{id}")
    User findById(Long id);

    @Select("select * from tb_user")
    List<User> findAll();
}
