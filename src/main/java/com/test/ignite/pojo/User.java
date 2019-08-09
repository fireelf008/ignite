package com.test.ignite.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.apache.ignite.cache.query.annotations.QuerySqlField;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "tb_user")
@EntityListeners(AuditingEntityListener.class)
@Data
public class User implements Serializable {

    @Id
//    @GeneratedValue(strategy = GenerationType.AUTO)
    @QuerySqlField(index = true)
    private Long id;

    @QuerySqlField(index = true)
    private String name;

    @QuerySqlField(index = true)
    private Integer age;

    @QuerySqlField(index = true)
    private String sex;

    @CreatedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @QuerySqlField(index = true)
    private Date createTime;

    @LastModifiedDate
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @QuerySqlField(index = true)
    private Date updateTime;
}
