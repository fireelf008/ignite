package com.test.controller;

import com.test.pojo.User;
import com.test.service.crud.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@Api(tags = "CRUD测试", description = "CRUD测试")
public class TestController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/crud/insert")
    @ApiOperation(value = "新增用户" , notes = "新增用户")
    public ResponseEntity<Void> insert(@ApiParam(name = "name" ,required = true , value = "姓名") @RequestParam(value = "name") String name,
                                       @ApiParam(name = "age" ,required = true , value = "年龄") @RequestParam(value = "age") Integer age,
                                       @ApiParam(name = "sex" ,required = true , value = "性别") @RequestParam(value = "sex") String sex) {
        log.info("==========>param is name:{}, age:{}, sex:{}", name, age, sex);
        this.userService.insert(name, age, sex);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping(value = "/crud/find-by-page")
    @ApiOperation(value = "分页查询用户" , notes = "分页查询用户")
    public ResponseEntity<List<User>> findByPage(@ApiParam(name = "page", value = "页数") @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                 @ApiParam(name = "size", value = "条数") @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        log.info("==========>param is page:{}, size:{}", page, size);
        List<User> userPage = this.userService.findByPage(page, size);
        return Optional.ofNullable(userPage).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/crud/find-by-id")
    @ApiOperation(value = "按id查询用户" , notes = "按id查询用户")
    public ResponseEntity<User> findById(@ApiParam(name = "id" ,required = true , value = "id") @RequestParam(value = "id") Long id) {
        log.info("==========>param is id:{}", id);
        User user = this.userService.findById(id);
        return Optional.ofNullable(user).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/crud/delete-by-id")
    @ApiOperation(value = "按id删除用户" , notes = "按id删除用户")
    public ResponseEntity<Void> deleteById(@ApiParam(name = "id" ,required = true , value = "id") @RequestParam(value = "id") Long id) {
        log.info("==========>param is id:{}", id);
        this.userService.deleteById(id);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping(value = "/crud/clear")
    @ApiOperation(value = "清空数据" , notes = "清空数据")
    public ResponseEntity<Void> clear() {
        this.userService.clear();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }
}
