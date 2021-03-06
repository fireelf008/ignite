package com.test.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.ignite.cache.query.annotations.QuerySqlField;

import java.io.Serializable;
import java.util.Date;

@Data
public class User implements Serializable {

    @JsonFormat(shape = JsonFormat.Shape.STRING)
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
