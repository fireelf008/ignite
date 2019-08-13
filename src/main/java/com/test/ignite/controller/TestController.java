package com.test.ignite.controller;

import com.test.ignite.pojo.User;
import com.test.ignite.service.JdbcTemplateService;
import com.test.ignite.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@Slf4j
@Api(tags = "ignite测试", description = "ignite测试")
public class TestController {

    @Autowired
    private UserService userService;

    @Autowired
    private JdbcTemplateService jdbcTemplateService;

    @PostMapping(value = "/insert")
    @ApiOperation(value = "新增用户" , notes = "新增用户")
    public ResponseEntity<Void> insert(@ApiParam(name = "name" ,required = true , value = "姓名") @RequestParam(value = "name") String name,
                                       @ApiParam(name = "age" ,required = true , value = "年龄") @RequestParam(value = "age") Integer age,
                                       @ApiParam(name = "sex" ,required = true , value = "性别") @RequestParam(value = "sex") String sex) {
        log.info("==========>param is name:{}, age:{}, sex:{}", name, age, sex);
        this.userService.insert(name, age, sex);
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping(value = "/findByPage")
    @ApiOperation(value = "分页查询用户" , notes = "分页查询用户")
    public ResponseEntity<List<User>> findByPage(@ApiParam(name = "page", value = "页数") @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                                                 @ApiParam(name = "size", value = "条数") @RequestParam(name = "size", required = false, defaultValue = "10") Integer size) {
        log.info("==========>param is page:{}, size:{}", page, size);
        List<User> userPage = this.userService.findByAgeGreaterThanEqual();
        return Optional.ofNullable(userPage).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping(value = "/create-table")
    @ApiOperation(value = "创建测试表" , notes = "创建测试表")
    public ResponseEntity<Void> createTable() {
        this.jdbcTemplateService.createTable();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping(value = "/insert-data")
    @ApiOperation(value = "插入测试数据" , notes = "插入测试数据")
    public ResponseEntity<Void> insertData() {
        this.jdbcTemplateService.insertData();
        return new ResponseEntity<Void>(HttpStatus.OK);
    }

    @GetMapping(value = "/query-data")
    @ApiOperation(value = "查询测试数据" , notes = "查询测试数据")
    public ResponseEntity<List<Map<String, Object>>> queryData() {
        List<Map<String, Object>> mapList = this.jdbcTemplateService.queryData();
        return Optional.of(mapList).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }
}
