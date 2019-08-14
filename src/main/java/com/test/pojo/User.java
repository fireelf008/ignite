package com.test.pojo;

import lombok.Data;
import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {

    @QuerySqlField(index = true)
    private Long id;

    @QuerySqlField
    private String name;

    @QuerySqlField
    private Integer age;

    @QuerySqlField
    private String sex;

    @QuerySqlField
    private Date createTime;

    @QuerySqlField
    private Date updateTime;
}
