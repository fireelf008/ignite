package com.test;

import com.test.utils.ApplicationContextUtils;
import org.apache.ignite.springdata20.repository.config.EnableIgniteRepositories;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableIgniteRepositories
@MapperScan(basePackages = "com.test.dao.mapper")
public class IgniteApplication {

	public static void main(String[] args) {
		SpringApplication.run(IgniteApplication.class, args);
	}
}
